package com.epw.academy.service.impl;

import com.epw.academy.dto.CreateCourseRequest;
import com.epw.academy.dto.CourseResponse;
import com.epw.academy.entity.Course;
import com.epw.academy.repository.CourseRepository;
import com.epw.academy.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public CourseResponse create(CreateCourseRequest request) {
        Course c = new Course();
        c.setName(request.getName());
        c.setDescription(request.getDescription());
        c.setDuration(request.getDuration());
        return toResponse(repository.save(c));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> list() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    private CourseResponse toResponse(Course c) {
        CourseResponse r = new CourseResponse();
        r.setId(c.getId());
        r.setName(c.getName());
        r.setDescription(c.getDescription());
        r.setDuration(c.getDuration());
        r.setCreatedAt(c.getCreatedAt());
        return r;
    }
}