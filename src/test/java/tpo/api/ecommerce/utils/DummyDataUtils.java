package tpo.api.ecommerce.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import tpo.api.ecommerce.domain.AuthenticationRequestDTO;
import tpo.api.ecommerce.domain.BuyDTO;
import tpo.api.ecommerce.domain.BuyStatusDTO;
import tpo.api.ecommerce.domain.CategoryProductDTO;
import tpo.api.ecommerce.domain.CreateBuyRequestDTO;
import tpo.api.ecommerce.domain.CreateBuyResponseDTO;
import tpo.api.ecommerce.domain.CreateItemProductDTO;
import tpo.api.ecommerce.domain.ItemProductDTO;
import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.domain.RegisterRequestDTO;
import tpo.api.ecommerce.domain.SubcategoryProductDTO;
import tpo.api.ecommerce.entity.Buy;
import tpo.api.ecommerce.entity.BuyStatus;
import tpo.api.ecommerce.entity.ItemProduct;
import tpo.api.ecommerce.entity.Product;
import tpo.api.ecommerce.entity.User;

public class DummyDataUtils {

    public static ProductDTO buildProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setProductName("Air Jordan 1 Low");
        productDTO.setPrice(new BigDecimal(150));
        productDTO.setUrlImage("urlImage");
        productDTO.setStock(1);
        productDTO.setDescription("Descripcion Air Jordan 1.");
        return productDTO;
    }

    public static Product buildProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setProductName("Air Jordan 1 Low");
        product.setPrice(new BigDecimal(150));
        product.setUrlImage("urlImage");
        product.setStock(1);
        product.setDescription("Descripcion Air Jordan 1.");
        return product;
    }

    public static List<CategoryProductDTO> buildCategoriesList() {
        return Arrays.asList(CategoryProductDTO.values());
    }

    public static List<SubcategoryProductDTO> buildSubcategoriesList() {
        return Arrays.asList(SubcategoryProductDTO.values());
    }

    public static CreateBuyRequestDTO buildCreateBuyRequestDTO() {
        CreateBuyRequestDTO request = new CreateBuyRequestDTO();
        CreateItemProductDTO item1 = new CreateItemProductDTO(1l, 1);
        List<CreateItemProductDTO> items = List.of(item1);
        request.setItems(items);
        return request;
    }

    public static CreateBuyResponseDTO buildCreateBuyResponseDTO() {
        CreateBuyResponseDTO response = new CreateBuyResponseDTO();
        response.setNumber(1l);
        response.setItems(buildItemsDTO());
        response.setTotal(BigDecimal.valueOf(100));
        response.setStatus(BuyStatusDTO.PENDING);
        return response;
    }

    public static ItemProductDTO buildItemProductDTO() {
        ItemProductDTO item = new ItemProductDTO();
        item.setProduct(buildProductDTO());
        item.setQuantity(1);
        return item;
    }

    public static List<ItemProductDTO> buildItemsDTO() {
        return List.of(buildItemProductDTO());
    }

    public static BuyDTO buildBuyDTO() {
        BuyDTO response = new BuyDTO();
        response.setNumber(1l);
        response.setItems(buildItemsDTO());
        response.setTotal(BigDecimal.valueOf(100));
        response.setStatus(BuyStatusDTO.PENDING);
        return response;
    }

    public static Buy buildBuy() {
        Buy response = new Buy();
        response.setNumber(1l);
        response.setItems(buildItems());
        response.setTotal(BigDecimal.valueOf(150));
        response.setStatus(BuyStatus.PENDING);
        return response;
    }

    public static ItemProduct buildItemProduct() {
        ItemProduct item = new ItemProduct();
        item.setProduct(buildProduct());
        item.setQuantity(1);
        return item;
    }

    public static List<ItemProduct> buildItems() {
        return List.of(buildItemProduct());
    }

    public static RegisterRequestDTO buildRegisterRequestDTO() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("Pepe");
        request.setLastName("Pepito");
        request.setEmail("pepepepito@test.com");
        request.setPassword("12345");
        return request;
    }

    public static AuthenticationRequestDTO buildAuthenticationRequestDTO() {
        AuthenticationRequestDTO request = new AuthenticationRequestDTO();
        request.setEmail("test@test.com");
        request.setPassword("12345");
        return request;
    }

    public static User buildUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Pepe");
        user.setLastName("Pepito");
        user.setEmail("test@test.com");
        user.setPassword("encryptedPassword");
        return user;
    }

}
