package tpo.api.ecommerce.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.CreateDiscountDTO;
import tpo.api.ecommerce.domain.DiscountDTO;
import tpo.api.ecommerce.domain.UpdateDiscountDTO;
import tpo.api.ecommerce.entity.Discount;
import tpo.api.ecommerce.error.DiscountCodeAlreadyExistsException;
import tpo.api.ecommerce.error.DiscountNotFoundException;
import tpo.api.ecommerce.mapper.DiscountMapper;
import tpo.api.ecommerce.repository.DiscountRepository;
import tpo.api.ecommerce.service.DiscountService;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository repository;

    private final DiscountMapper mapper;

    public List<DiscountDTO> getAllDiscounts() {
        return repository.findAll().stream()
                .map(mapper::toDiscountDTO)
                .toList();
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public DiscountDTO createDiscountCode(CreateDiscountDTO dto) {
        Discount discount = mapper.toDiscountFromCreateDTO(dto);

        if (Boolean.TRUE.equals(codeExists(dto.getCode()))) {
            throw new DiscountCodeAlreadyExistsException(dto.getCode());
        }

        return mapper.toDiscountDTO(repository.save(discount));
    }

    @Override
    public DiscountDTO getDiscountById(Long discountId) {
        Discount discount = repository.findById(discountId)
                .orElseThrow(DiscountNotFoundException::new);

        return mapper.toDiscountDTO(discount);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public DiscountDTO updateDiscountCode(Long discountId, UpdateDiscountDTO dto) {
        Discount discount = repository.findById(discountId)
                .orElseThrow(DiscountNotFoundException::new);

        if (dto.getCode() != null
                && codeExists(dto.getCode())) {
            throw new DiscountCodeAlreadyExistsException(dto.getCode());
        }

        return mapper.toDiscountDTO(
                repository.save(
                        mapper.toDiscountFromUpdateDTO(dto, discount)));
    }

    @Override
    public void deleteDiscountCode(Long discountId) {
        Discount discount = repository.findById(discountId)
                .orElseThrow(DiscountNotFoundException::new);
        repository.delete(discount);
    }

    private Boolean codeExists(String code) {
        return repository.findByCodeIgnoreCase(code).isPresent();
    }

}
