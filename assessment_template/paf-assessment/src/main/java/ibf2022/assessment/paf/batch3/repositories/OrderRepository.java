package ibf2022.assessment.paf.batch3.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	// TODO: Task 5
	public Document insertBeerOrders(String orderJson) {
		Document d = Document.parse(orderJson);
		Document insertedDoc = mongoTemplate.insert(d, "orders");
		return insertedDoc;
	}

}
