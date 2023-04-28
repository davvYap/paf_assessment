package ibf2022.assessment.paf.batch3.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2022.assessment.paf.batch3.models.Beer;
import ibf2022.assessment.paf.batch3.models.Brewery;
import ibf2022.assessment.paf.batch3.models.Style;
import static ibf2022.assessment.paf.batch3.repositories.DBQueries.*;

@Repository
public class BeerRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// DO NOT CHANGE THE SIGNATURE OF THIS METHOD
	public List<Style> getStyles() {
		// TODO: Task 2
		List<Style> styles = new ArrayList<>();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_BEER_STYLE_COUNT);
		while (rs.next()) {
			styles.add(createFromResulttoStyle(rs));
		}
		return styles;
	}

	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	public List<Beer> getBreweriesByBeer(String styleName) {
		// TODO: Task 3
		List<Beer> beers = new ArrayList<>();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_BREWERIES_BY_BEER, styleName);
		while (rs.next()) {
			beers.add(convertFromResulttoBeer(rs));
		}
		return beers;
	}

	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	public Optional<Brewery> getBeersFromBrewery(int breweryId) {
		// TODO: Task 4
		Brewery br = null;
		List<Beer> beers = new ArrayList<>();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_BEERS_FROM_BREWERY, breweryId);

		while (rs.next()) {
			br = convertFromResulttoBrewery(rs);
			beers.add(convertFromResulttoBeer(rs));
		}
		if (br != null) {
			br.setBeers(beers);
		}
		return Optional.ofNullable(br);
	}

	public Style createFromResulttoStyle(SqlRowSet rs) {
		Style style = new Style();
		style.setStyleId(rs.getInt("style_id"));
		style.setName(rs.getString("style"));
		style.setBeerCount(rs.getInt("beer_count"));
		return style;
	}

	public Beer convertFromResulttoBeer(SqlRowSet rs) {
		Beer b = new Beer();
		b.setBeerId(rs.getInt("beer_id"));
		b.setBeerName(rs.getString("beer_name"));
		b.setBeerDescription(rs.getString("beer_description"));
		b.setBreweryId(rs.getInt("brewery_id"));
		b.setBreweryName(rs.getString("brewery_name"));
		return b;
	}

	public Brewery convertFromResulttoBrewery(SqlRowSet rs) {
		Brewery br = new Brewery();
		br.setBreweryId(rs.getInt("brewery_id"));
		br.setName(rs.getString("brewery_name"));
		br.setAddress1(rs.getString("address1"));
		br.setAddress2(rs.getString("address2"));
		br.setCity(rs.getString("city"));
		br.setPhone(rs.getString("phone"));
		br.setWebsite(rs.getString("website"));
		br.setDescription(rs.getString("brewery_description"));
		return br;
	}
}
