package io.javabrains.moviecatalogueservice.resources;

import io.javabrains.moviecatalogueservice.models.CatalogueItem;
import io.javabrains.moviecatalogueservice.models.Movie;
import io.javabrains.moviecatalogueservice.models.Rating;
import io.javabrains.moviecatalogueservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogueResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;
    @RequestMapping("/{userId}")
    public List<CatalogueItem> getCatalogue(@PathVariable("userId") String userId)
    {
        // Get all movie IDs which are rated;

        //Put them all together

        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
                    //In the below code we are unmarshalling the string response to a movie object using ".getForObject()"
                    // For each movie ID get the details from the movie-info-service
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
                    //Put them all together
                    return new CatalogueItem(movie.getName(), "Test", rating.getRating());
                })
                .collect(Collectors.toList());

/*        return singletonList(
                new CatalogueItem("Transformers", "Test", 4)
        );*/
    }
}
