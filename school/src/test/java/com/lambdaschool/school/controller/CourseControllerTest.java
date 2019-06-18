package com.lambdaschool.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.CourseService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CourseController.class, secure = false)
public class CourseControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private List<Instructor> instructorList;
    private ArrayList<Course> courseList;

    @Before
    public void setUp() throws Exception
    {
        instructorList = new ArrayList<>();
        courseList = new ArrayList<>();

        Instructor instType1 = new Instructor("John");
        instType1.setInstructid(1);
        Instructor instType2 = new Instructor("Mary");
        instType2.setInstructid(2);

        instructorList.add(instType1);
        instructorList.add(instType2);


        String course1Name = "Android";
        String course2Name = "Java";
        Course c1 = new Course(course1Name, instType1);
        Course c2 = new Course(course2Name, instType2);

        courseList.add(c1);
        courseList.add(c2);

    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllCourses() throws Exception
    {
        String apiUrl = "/courses/courses";

        Mockito.when(courseService.findAll()).thenReturn(courseList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();

        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(courseList);
    }

    @Test
    public void getCountStudentsInCourses()
    {
    }

    @Test
    public void deleteCourseById()
    {
    }

    @Test
    public void addNewCourse() throws Exception
    {
        String apiUrl = "/courses/course/course/add";

        ArrayList<Course> thisCourse = new ArrayList<>();
        String course3Name = "Javascript";
        Instructor instType3 = new Instructor("Bob");
        instType3.setInstructid(3);
        Course c3 = new Course(course3Name, instType3);
        c3.setCourseid(100);
        ObjectMapper mapper = new ObjectMapper();
        String courseString = mapper.writeValueAsString(c3);

        Mockito.when(courseService.save(any(Course.class))).thenReturn(c3);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(courseString);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }
}