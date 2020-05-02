package com.example.collegeautomationsystem.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the instructor database table.
 *
 */

@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@NamedQuery(name="Instructor.findAll", query="SELECT i FROM Instructor i")
public class Instructor implements Serializable {


    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(unique=true, nullable=false)
    private Long id;


//	@Column(name="department_id", unique=true)
//	private int departmentId;


    @OneToMany(mappedBy="instructor" , fetch=FetchType.EAGER)
    private List<Course> courses;

    private String rank;

    private String coursesTaught;

    private String researchInterests;

    private String education;

    private String timeAtTanta;

    private String currentActivities;

    private String currentResearch;

    private String Code ;

    public String getCoursesTaught() {
        return coursesTaught;
    }

    public void setCoursesTaught(String coursesTaught) {
        this.coursesTaught = coursesTaught;
    }

    public String getResearchInterests() {
        return researchInterests;
    }

    public void setResearchInterests(String researchInterests) {
        this.researchInterests = researchInterests;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getTimeAtTanta() {
        return timeAtTanta;
    }

    public void setTimeAtTanta(String timeAtTanta) {
        this.timeAtTanta = timeAtTanta;
    }

    public String getCurrentActivities() {
        return currentActivities;
    }

    public void setCurrentActivities(String currentActivities) {
        this.currentActivities = currentActivities;
    }



    @Transient
    private MultipartFile file ;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getCurrentResearch() {
        return currentResearch;
    }

    public void setCurrentResearch(String currentResearch) {
        this.currentResearch = currentResearch;
    }

//	@Column(name="instructor_Img", length=255)
//	private String instructorImg;

    @Column(length = 255)
    private String otherTitles ;


    //bi-directional one-to-one association to Department
//	@OneToOne(mappedBy="instructor")
//	private Department department;

    //bi-directional one-to-one association to Section
//	@OneToOne
//	@JoinColumn(name="id", nullable=false, insertable=false, updatable=false)
//	private Section section;

    //bi-directional many-to-one association to User
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private Employee employee;

    public Instructor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //	public int getDepartmentId() {
//		return this.departmentId;
//	}
//
//	public void setDepartmentId(int departmentId) {
//		this.departmentId = departmentId;
//	}

    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

//	public String getInstructorImg() {
//		return this.instructorImg;
//	}
//
//	public void setInstructorImg(String instructorImg) {
//		this.instructorImg = instructorImg;
//	}

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getOtherTitles() {
        return otherTitles;
    }

    public void setOtherTitles(String otherTitles) {
        this.otherTitles = otherTitles;
    }




//	public Department getDepartment() {
//		return this.department;
//	}
//
//	public void setDepartment(Department department) {
//		this.department = department;
//	}
//
//	public Section getSection() {
//		return this.section;
//	}
//
//	public void setSection(Section section) {
//		this.section = section;
//	}

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
