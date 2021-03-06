package com.coffemachine.controller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.coffemachine.dto.CardDTO;
import com.coffemachine.mail.MailSender;
import com.coffemachine.model.Card;
import com.coffemachine.model.User;
import com.coffemachine.services.CardService;
import com.coffemachine.services.UserService;

@RestController
@RequestMapping("/api/card")
public class CardController {

	@Autowired
	CardService cardService;
	@Autowired
	UserService userService;
	
	@Autowired
	@Qualifier("MyMailSender")
	MailSender mailSender;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/")
	public List<CardDTO> getRestAllCards() {
		return cardService.getAllCards();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/{uid}")
	public Card getCardByUid(@PathVariable String uid) {
		return cardService.getCardByUID(uid);

	}
	
	//for station only
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping("/station/{uid}")
	public Card getCardByUidForStation(@PathVariable String uid) {
		return cardService.getCardByUID(uid);

	}
	
	//for only station
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping("user/{uid}")
	public User getUserByCardUid(@PathVariable String uid){
		Card card = cardService.getCardByUID(uid);
		if(card != null){
			User user = card.getUser();
			return user;
		}
		return null;
	}
	
	//for station only
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.POST, value ="/email/{email:.+}") 
	public void addCardByEmail(@PathVariable String email, @RequestBody Card card){
		User user = userService.getByEmail(email); 
		if(user != null){
		card.setUser(user);
		card.setCreatedDate(new Date());
		cardService.addCard(card); //cardService.updateCard(card);
		user.getCards().add(card); 
		userService.updateUser(user); 
		
		try {

			String from = "khasanboyakbarov@gmail.com";
			String to = user.getEmail();
			String subject = "Greeting";
			String body = "New card is added to your user account in eKohvik!";
			mailSender.sendMail(from, to, subject, body);

		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		}
	}
	
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	 @RequestMapping(method = RequestMethod.GET, value="/email/{email}") 
	 public List<Card> getAllCardsByUser(@PathVariable String email) { 
		 return cardService.getAllCardsByUserEmail(email); 
	 }

	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	 @RequestMapping(method = RequestMethod.PUT, value = "/")
	 public void updateCard(@RequestBody Card card) {
		 cardService.updateCard(card); 
	 }

	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	 @RequestMapping(method = RequestMethod.DELETE, value = "/{id}") 
	 public void deleteCard(@PathVariable Long id) {
		 Card card = cardService.getCard(id);
		 User user = userService.getUser(card.getUser().getUserId());
		 user.getCards().remove(card);
		 cardService.deleteCard(id); 
		 
		 try {

				String from = "khasanboyakbarov@gmail.com";
				String to = user.getEmail();
				String subject = "Greeting";
				String body = "Card is deleted from you user account in eKohvik!";
				mailSender.sendMail(from, to, subject, body);

			} catch (Exception ex) {
				System.out.println(ex);
			}
		 
	 }
}
