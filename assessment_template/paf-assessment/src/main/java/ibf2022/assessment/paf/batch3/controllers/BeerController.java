package ibf2022.assessment.paf.batch3.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ibf2022.assessment.paf.batch3.models.Beer;
import ibf2022.assessment.paf.batch3.models.Brewery;
import ibf2022.assessment.paf.batch3.models.Order;
import ibf2022.assessment.paf.batch3.models.Style;
import ibf2022.assessment.paf.batch3.repositories.BeerRepository;
import ibf2022.assessment.paf.batch3.services.BeerService;

@Controller
@RequestMapping(path = "/")
public class BeerController {

	@Autowired
	BeerRepository beerRepository;

	@Autowired
	BeerService beerService;

	// TODO Task 2 - view 0
	@GetMapping
	public String getStyles(Model model, @ModelAttribute Style s) {
		List<Style> styles = beerRepository.getStyles();
		model.addAttribute("styles", styles);
		model.addAttribute("s", s);
		return "view0";
	}

	// TODO Task 3 - view 1
	@GetMapping(path = "/beer/style/{id}")
	public String getBreweriesByBeer(Model model, @ModelAttribute Beer b, @PathVariable String id,
			@RequestParam String styleName) {
		List<Beer> beers = beerRepository.getBreweriesByBeer(styleName);
		model.addAttribute("styleName", styleName);
		model.addAttribute("b", b);
		if (beers.isEmpty()) {
			model.addAttribute("details", "No beers found for this style");
			return "view1";
		}
		model.addAttribute("beers", beers);
		return "view1";
	}

	// TODO Task 4 - view 2
	@GetMapping(path = "/beer/brewery/{id}")
	public String getBeersByBrewery(Model model, @ModelAttribute Brewery br, @PathVariable String id,
			@RequestParam String breweryName) {
		Brewery brewery = null;
		if (beerRepository.getBeersFromBrewery(Integer.parseInt(id)).isEmpty()) {
			model.addAttribute("details", "Brewery not found");
			return "view2";
		}
		brewery = beerRepository.getBeersFromBrewery(Integer.parseInt(id)).get();
		model.addAttribute("br", brewery);
		return "view2";
	}

	// TODO Task 5 - view 2, place order
	@PostMapping(path = "brewery/{breweryId}/order", consumes = "application/x-www-form-urlencoded")
	public String placeOrder(Model model, @PathVariable String breweryId,
			@RequestBody MultiValueMap<String, String> map) {
		List<Beer> beers = beerRepository.getBeersFromBrewery(Integer.parseInt(breweryId)).get().getBeers();

		List<Order> orders = new ArrayList<>();

		for (int i = 0; i < map.size(); i++) {
			if (!map.getFirst("amount%d".formatted(i)).isEmpty()
					&& Integer.parseInt(map.getFirst("amount%d".formatted(i))) != 0) {
				int id = beers.get(i).getBeerId();
				int amountOrdered = Integer.parseInt(map.getFirst("amount%d".formatted(i)));
				Order order = new Order(id, amountOrdered);
				orders.add(order);
			}
		}

		if (orders.isEmpty()) {
			model.addAttribute("empty", "Your order cart is empty");
			return "view3";
		}

		String orderId = beerService.placeOrder(Integer.parseInt(breweryId), orders);
		model.addAttribute("orderId", orderId);
		return "view3";
	}
}
