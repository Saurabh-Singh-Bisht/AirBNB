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
        if(hotelMap.containsKey(hotel.getHotelName()))
            return "FAILURE";
        hotelMap.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public Integer add(User user) {
        userMap.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }

    public int findMaxFacility() {
        int max =0;
        for (String hotel: hotelMap.keySet()){
            Hotel hotel1 = hotelMap.get(hotel);
            max = Math.max(max, hotel1.getFacilities().size());
        }
        return max;
    }

    public List<String> getHotelWithSameFacility(int maxFacilityByAnHotel) {
        List<String> hotelList = new ArrayList<>();
        for (String hotel: hotelMap.keySet()){
            Hotel hotel1 = hotelMap.get(hotel);
            if (maxFacilityByAnHotel == hotel1.getFacilities().size()){
                hotelList.add(hotel1.getHotelName());
            }
        }
        return hotelList;
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
        int reqRooms = booking.getNoOfRooms();
        Hotel hotel = hotelMap.get(booking.getHotelName());
        if(hotel.getAvailableRooms() < reqRooms)
            return -1;

        booking.setBookingId(bookingId);
        int totalAmount = reqRooms * hotel.getPricePerNight();
        booking.setAmountToBePaid(reqRooms * hotel.getPricePerNight());
        bookingMap.put(bookingId, booking);

        hotel.setAvailableRooms(hotel.getAvailableRooms()-reqRooms);
        hotelMap.put(hotel.getHotelName(), hotel);

        int userId = booking.getBookingAadharCard();
        if(userBookingCount.containsKey(userId)){
            userBookingCount.put(userId, 1);
        }
        else {
            userBookingCount.put(userId, userBookingCount.get(userId)+1);
        }

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
        return hotel;
    }
}
