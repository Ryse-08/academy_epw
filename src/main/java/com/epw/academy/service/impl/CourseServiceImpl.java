package com.epw.academy.service.impl;

import com.epw.academy.dto.CreateCourseRequest;
import com.epw.academy.dto.CourseResponse;
import com.epw.academy.entity.Course;
import com.epw.academy.entity.Instructor;
import com.epw.academy.exception.ResourceNotFoundException;
import com.epw.academy.repository.CourseRepository;
import com.epw.academy.repository.InstructorRepository;
import com.epw.academy.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final InstructorRepository instructorRepository;

    public CourseServiceImpl(CourseRepository repository, InstructorRepository instructorRepository) {
        this.repository = repository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public CourseResponse create(CreateCourseRequest request) {
        Instructor instructor = instructorRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Instructor " + request.getInstructorId() + " not found"));

        Course c = new Course();
        c.setName(request.getName());
        c.setDescription(request.getDescription());
        c.setDuration(request.getDuration());
        c.setInstructor(instructor);
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
        if (c.getInstructor() != null) {
            r.setInstructorId(c.getInstructor().getId());
            r.setInstructorName(c.getInstructor().getName());
        }
        return r;
    }
}