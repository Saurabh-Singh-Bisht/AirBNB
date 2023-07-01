package com.driver.repository;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {
    private Map<String, Hotel> hotelMap = new HashMap<>();
    private Map<Integer, User> userMap = new HashMap<>();
    private Map<String, Booking> bookingMap = new HashMap<>();
    private Map<Integer, Integer> userBookingCount = new HashMap<>();

    public String add(Hotel hotel) {
        if(hotel==null || hotel.getHotelName()==null){
            return "FAILURE";
        }

        if(hotelMap.containsKey(hotel.getHotelName()))
            return "FAILURE";

        hotelMap.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public Integer add(User user) {
        userMap.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }

    public String mostFacility() {
        int maxFacility =0;
        String ans ="";
        for (Hotel hotel: hotelMap.values()){
            if(hotel.getFacilities().size() > maxFacility){
                maxFacility = hotel.getFacilities().size();
                ans = hotel.getHotelName();
            }
            else if(hotel.getFacilities().size() == maxFacility){
                if (hotel.getHotelName().compareTo(ans) < 0)
                    ans = hotel.getHotelName();
            }
        }
        return ans;
    }

    public int bookARoom(Booking booking) {
        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);

        int reqRooms = booking.getNoOfRooms();

        Hotel hotel = hotelMap.get(booking.getHotelName());
        if(hotel.getAvailableRooms() < reqRooms)
            return -1;

        int totalAmount = reqRooms * hotel.getPricePerNight();

        booking.setAmountToBePaid(totalAmount);
        bookingMap.put(bookingId, booking);

        hotel.setAvailableRooms(hotel.getAvailableRooms()-reqRooms);
        hotelMap.put(hotel.getHotelName(), hotel);

        int userId = booking.getBookingAadharCard();
        int currentBooking = userBookingCount.get(userId);
        userBookingCount.put(userId, Objects.nonNull(currentBooking)?1+currentBooking: 1);

        return totalAmount;
    }

    public int getBookingCount(Integer aadharCard) {
        return userBookingCount.get(aadharCard);
    }

    public Hotel updateFacility(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelMap.get(hotelName);
        List<Facility> oldFacilities = hotel.getFacilities();
        for (Facility facility: newFacilities){
            if (!oldFacilities.contains(facility)){
                oldFacilities.add(facility);
            }
        }
        hotelMap.put(hotelName, hotel);
        return hotel;
    }
}
