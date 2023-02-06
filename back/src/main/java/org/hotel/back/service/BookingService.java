package org.hotel.back.service;

import org.hotel.back.data.request.BookingRequestDTO;
import org.hotel.back.data.response.BookingResponseDTO;
import org.hotel.back.domain.Booking;
import java.util.List;


public interface BookingService {
    public List<Booking> findAll();
    public BookingResponseDTO read(Long id);
    public boolean save(BookingRequestDTO bookingRequestDTO);
    public boolean modify(BookingRequestDTO bookingRequestDTO);
    public boolean delete(Long id);
}
