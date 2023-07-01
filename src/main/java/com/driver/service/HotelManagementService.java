package com.driver.service;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repository.HotelManagementRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class HotelManagementService {
    private HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();

    public String addHotel(Hotel hotel) {
        if (hotel.getHotelName() == null || hotel == null)
            return "FAILURE";
        return hotelManagementRepository.add(hotel);
    }

    public Integer addUser(User user) {
        return hotelManagementRepository.add(user);
    }

    public String hotelWithMostFacility() {
        return hotelManagementRepository.mostFacility();
    }

    public int bookARoom(Booking booking) {
        return hotelManagementRepository.bookARoom(booking);
    }

    public int getCountOfBooking(Integer aadharCard) {
        return hotelManagementRepository.getBookingCount(aadharCard);
    }

    public Hotel updateFacility(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelManagementRepository.updateFacility(newFacilities, hotelName);
        return hotel;
    }
}
