package com.fredandcarol.fredandcarolapi.booking;

import org.springframework.stereotype.Service;

@Service
public class Bookings {

    public BookingsCalendar getBookingsCalendar() {
          return new BookingsCalendar();
    }
}
