package bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendCartRequest {

	private String productId;
	private String price;
	private String quantity;
	private String size;
	private String color;
	private String shopperProfileId;
	private String cartId;
}
