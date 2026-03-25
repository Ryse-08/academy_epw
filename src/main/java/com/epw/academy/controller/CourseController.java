package com.epw.academy.controller;

import com.epw.academy.dto.CreateCourseRequest;
import com.epw.academy.dto.CourseResponse;
import com.epw.academy.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse create(@Valid @RequestBody CreateCourseRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<CourseResponse> list() {
        return service.list();
    }
}