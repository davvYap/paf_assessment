package ibf2022.assessment.paf.batch3.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.assessment.paf.batch3.models.Order;
import ibf2022.assessment.paf.batch3.repositories.OrderRepository;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Service
public class BeerService {
	@Autowired
	private OrderRepository orderRepository;

	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	public String placeOrder(int breweryId, List<Order> orders) {
		// TODO: Task 5
		String orderId = UUID.randomUUID().toString().substring(0, 8);
		Date orderDate = new Date();

		JsonArrayBuilder jsArr = Json.createArrayBuilder();
		for (Order order : orders) {
			jsArr.add(order.orderToJsonObjectBuilder());
		}

		JsonObject jsObj = Json.createObjectBuilder()
				.add("orderId", orderId)
				.add("date", orderDate.toString())
				.add("breweryId", breweryId)
				.add("orders", jsArr)
				.build();

		orderRepository.insertBeerOrders(jsObj.toString());
		return orderId;
	}

}
