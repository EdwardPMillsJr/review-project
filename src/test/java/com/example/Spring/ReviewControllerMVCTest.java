package com.example.Spring;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import models.Review;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerMVCTest {
	
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private Review review1;
	
	@Mock
	private Review review2;
	
	@MockBean
	private ReviewRepository reviewRepo;
	
	@Test
	public void shouldGetStatusOfOkWhenNavigatingToAllReviews() throws Exception {
		this.mockMvc.perform(get("/show-reviews")).andExpect(status().isOk())
		.andExpect(view().name("reviews-template"));
	}

	@Test
	public void shouldGetStatusOfOkWhenNavigatingToSingleReviewPage() throws Exception{
		when(reviewRepo.findOneReview(1)).thenReturn(review1);
		this.mockMvc.perform(get("/review/1")).andExpect(status().isOk())
		.andExpect(view().name("review"));
	}
	
	@Test
	public void shouldAddAllReviewsToTheModel() throws Exception {
		when(reviewRepo.findAllReviews()).thenReturn(Arrays.asList(review1,review2));
		this.mockMvc.perform(get("/show-reviews")).andExpect(model().attribute("reviewModel", hasSize(2)));
	}
	
	@Test
	public void shouldAddSingleReviewToTheModel() throws Exception {
		when(reviewRepo.findOneReview(1)).thenReturn(review1);
		this.mockMvc.perform(get("/review/1")).andExpect(model().attribute("reviewModel",is(review1)));
	}

	
	@Test
	public void shouldReturnNotFoundForBadRequest() throws Exception {
	Long badId = 5L;
	when(reviewRepo.findOneReview(badId)).thenReturn(null);
	this.mockMvc.perform(get("/review?id=5"))
	.andExpect(status().isNotFound());
	}
	
	

}
