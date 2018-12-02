package pl.sdacademy.gamechanger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sdacademy.gamechanger.model.AppUser;
import pl.sdacademy.gamechanger.model.Auction;
import pl.sdacademy.gamechanger.model.ContactDetails;
import pl.sdacademy.gamechanger.model.dto.NewUserDTO;
import pl.sdacademy.gamechanger.repository.AppUserRepository;
import pl.sdacademy.gamechanger.repository.ContactDetailsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    public boolean addUser(NewUserDTO appUser){

        Optional<AppUser> userByUsername = userRepository.findAppUserByUsername(appUser.getUsername());
         if (userByUsername.isPresent()){
             return false;
         }

         AppUser newUser = new AppUser(appUser.getUsername(),bCryptPasswordEncoder.encode(appUser.getPassword()),0);
         newUser.setContactDetails(new ContactDetails(appUser.getEmail()));
         newUser.setPrivilege(0);
         contactDetailsRepository.save(newUser.getContactDetails());
         userRepository.save(newUser);

         return true;
    }

    public Optional<AppUser> findOptionalByUsername(String username) {
        return userRepository.findAppUserByUsername(username);
    }

    public AppUser findByUsername(String username) {
        Optional<AppUser> userOptional = userRepository.findAppUserByUsername(username);
        AppUser user = userOptional.get();
        return user;
    }



    public void addAuction(AppUser user, Auction auction) {
        user.getAuctions().add(auction);
        userRepository.save(user);
    }

    public List<AppUser> findAllByUsernameContaining(String username) {
        return userRepository.findAllByUsernameContaining(username);
    }

    public boolean makeExpert(long id) {
        Optional<AppUser> optionalOfUser = userRepository.findById(id);

        if (optionalOfUser.isPresent()) {
            AppUser appUser = optionalOfUser.get();
            appUser.setPrivilege(2);
            userRepository.save(appUser);
            return true;
        }
        return false;
    }


    public boolean makeAdmin(long id) {
        Optional<AppUser> optionalOfUser = userRepository.findById(id);

        if (optionalOfUser.isPresent()) {
            AppUser appUser = optionalOfUser.get();
            appUser.setPrivilege(3);
            userRepository.save(appUser);
            return true;
        }
        return false;
    }

    public boolean makeUser(long id) {
        Optional<AppUser> optionalOfUser = userRepository.findById(id);

        if (optionalOfUser.isPresent()) {
            AppUser appUser = optionalOfUser.get();
            appUser.setPrivilege(1);
            userRepository.save(appUser);
            return true;
        }
        return false;
    }

    public AppUser changeDetails(AppUser user, ContactDetails contactDetails) {
        ContactDetails userDetails = user.getContactDetails();

        if (userDetails == null) {
            userDetails = new ContactDetails();
        }

        if (contactDetails.getCity() != null) {
            userDetails.setCity(contactDetails.getCity());
        }


        if (contactDetails.getEmail() != null) {
            userDetails.setEmail(contactDetails.getEmail());
        }

        if (contactDetails.getHome() != null) {
            userDetails.setHome(contactDetails.getHome());
        }

        if (contactDetails.getPhoneNumber() != null) {
            userDetails.setPhoneNumber(contactDetails.getPhoneNumber());
        }

        if (contactDetails.getStreet() != null) {
            userDetails.setStreet(contactDetails.getStreet());
        }

        if (contactDetails.getZipCode() != null) {
            userDetails.setZipCode(contactDetails.getZipCode());
        }
        contactDetailsRepository.save(userDetails);
        user.setContactDetails(userDetails);
        return userRepository.save(user);
    }

    public Optional<AppUser> findByPassword(String code) {
        return userRepository.findByPassword(code);
    }
}
