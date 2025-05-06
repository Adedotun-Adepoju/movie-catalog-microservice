package com.example.movie_catalog_service.controller;

import com.example.movie_catalog_service.models.CatalogItem;
import com.example.movie_catalog_service.models.Movie;
import com.example.movie_catalog_service.models.Rating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
  private final RestTemplate restTemplate;
  private final WebClient.Builder webClientBuilder;

  public MovieCatalogController(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
    this.restTemplate = restTemplate;
    this.webClientBuilder = webClientBuilder;
  }

  @GetMapping("/{userId}")
  public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

    List<Rating> ratings = Arrays.asList(
            new Rating("12345", 4),
            new Rating("5678", 3)
    );

    return ratings.stream().map(rating -> {
      String url = String.format("http://localhost:8082/movies/%s", rating.getMovieId());
//      Movie movie = restTemplate.getForObject(url, Movie.class);
      Movie movie = webClientBuilder.build()
              .get()
              .uri(url)
              .retrieve() // go fetch the data from the url
              .bodyToMono(Movie.class) // whatever body you get back, convert it into an instance of the movie class. A Mono is like a promise that you are going to get back an object in the future. It doesn't give you the actual object now, but it give you in the future
              .block(); // the block function is what actually tells spring to wait until the Mono returns the expected value. It's more like you're making the request synchronous.
      return new CatalogItem(movie.getName(), "Text", rating.getRating());
    })
            .collect(Collectors.toList());
    // For each movie ID, call movie info service and get details

    // put them all together
  }
}
