package ibf2022.assessment.paf.batch3.repositories;

public class DBQueries {
    public static final String GET_BEER_STYLE_COUNT = """
            select s.id as 'style_id', s.style_name as 'style', count(b.name) as 'beer_count'
            from beers as b join styles as s on b.style_id = s.id
            group by s.style_name, s.id
            order by count(name) desc, s.style_name asc;
                            """;

    public static final String GET_BREWERIES_BY_BEER = """
            select b.id as 'beer_id', s.style_name as 'style_name', b.name as 'beer_name', b.descript as 'beer_description',
            br.id as 'brewery_id' ,br.name as 'brewery_name' from beers as b
            join breweries as br on b.brewery_id = br.id
            join styles as s on b.style_id = s.id
            where s.style_name = ?
            order by b.name asc;
                    """;

    public static final String GET_BEERS_FROM_BREWERY = """
            select br.id as 'brewery_id', br.name as 'brewery_name', br.address1, br.address2, br.city, br.phone, br.website, br.descript as 'brewery_description',
            b.id as 'beer_id', b.name as 'beer_name', b.descript as 'beer_description'
            from breweries as br
            join beers as b
            on br.id = b.brewery_id
            where br.id = ?
            order by b.name asc;
                """;
}
