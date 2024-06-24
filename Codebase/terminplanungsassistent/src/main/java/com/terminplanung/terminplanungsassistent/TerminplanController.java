package com.terminplanung.terminplanungsassistent;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terminplanung.exceptions.CalendarNotFoundException;

@RestController
@RequestMapping("/terminplan")
public class TerminplanController {

    // --- REST METHODS ---

    // POST LOGIN
    @SuppressWarnings("null")
    @PostMapping("/login")
    public HttpStatus validateLogin(@RequestBody String requestBody) {
        //TODO: implement validation logic

        return HttpStatus.OK;
    }


    // GET CALENDAR
    @GetMapping("/fetch")
    public ResponseEntity<String> getCalendar(@PathVariable Long id) throws CalendarNotFoundException {
        return new ResponseEntity<>(calendarRepository.findById(id)
        .orElseThrow(() -> new CalendarNotFoundException(id)), HttpStatus.OK);
    };


    // PUT AUSFALL MELDEN



    // // Falls noch Zeit: GET VERFÜGBARKEIT
}