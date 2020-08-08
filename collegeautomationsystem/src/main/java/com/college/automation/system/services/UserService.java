package com.college.automation.system.services;

import com.college.automation.system.dtos.AddressDto;
import com.college.automation.system.dtos.StudentProfileDto;
import com.college.automation.system.dtos.UpdateStudentProfileDto;
import com.college.automation.system.dtos.UserProfileDto;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.*;
import com.college.automation.system.repos.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private MessageSource messageSource;

    public UserProfileDto getUserProfile(String username) {
        User customer = userRepo.findByEmail(username);

        if (null != customer) {
            UserProfileDto userProfileDto = new UserProfileDto();
            BeanUtils.copyProperties(customer, userProfileDto);
            return userProfileDto;
        } else {
            throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
        }
    }

    public UserProfileDto getUserProfile1(Long userId) {
        Optional<User> customer = userRepo.findById(userId);
        if (customer.isPresent()) {
            UserProfileDto userProfileDto = new UserProfileDto();
            userProfileDto.setActive(customer.get().isActive());
            userProfileDto.setFirstName(customer.get().getFirstName());
            userProfileDto.setLastName(customer.get().getLastName());
            userProfileDto.setMobileNo(customer.get().getMobileNo());
            userProfileDto.setUserId(customer.get().getUserId());
            userProfileDto.setUsername(customer.get().getUsername());
            return userProfileDto;
        } else {
            throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
        }
    }

    public Set<Address> getUserAddress(Long userId) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isPresent()) {
            Set<Address> addresses = new LinkedHashSet<>();

            for(Address address : user.get().getAddresses()){
                if(!address.isDeleted()) {
                    addresses.add(address);
                }
            }
            return addresses;
        } else {
            throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
        }
    }

    public StudentProfileDto getStudentProfile(String username){
        Optional<Student> student = Optional.ofNullable(studentRepo.findByEmail(username));

        if (student.isPresent()) {
            StudentProfileDto studentProfileDto = new StudentProfileDto();

            studentProfileDto.setUsername(student.get().getUsername());
            studentProfileDto.setFirstName(student.get().getFirstName());
            studentProfileDto.setLastName(student.get().getLastName());
            studentProfileDto.setBranch(student.get().getBranches().getBranchName());
            studentProfileDto.setMobileNo(student.get().getMobileNo());
            studentProfileDto.setSection(student.get().getSection());
            studentProfileDto.setYear(student.get().getYear());
            studentProfileDto.setActive(student.get().isActive());
            studentProfileDto.setUserId(student.get().getUserId());
            studentProfileDto.setSemester(student.get().getSemester());
            return studentProfileDto;
        }else {
            throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
        }
    }


    public Set<Address> getUserAddress(String username) {
        Optional<User> user = Optional.ofNullable(userRepo.findByEmail(username));

        if (user.isPresent()) {
            Set<Address> addresses = new LinkedHashSet<>();

            for(Address address : user.get().getAddresses()){
                if(!address.isDeleted()) {
                    addresses.add(address);
                }
            }
            return addresses;
        } else {
            throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
        }
    }

    @Transactional
    @Modifying
    public String updateStudent(UpdateStudentProfileDto userProfileDto, String username) {
        Optional<Student> studentExist = Optional.ofNullable(studentRepo.findByEmail(username));

        StringBuilder sb = new StringBuilder();
        if (studentExist.isPresent()) {
            studentExist.get().setFirstName(userProfileDto.getFirstName());
            studentExist.get().setLastName(userProfileDto.getLastName());
            studentExist.get().setMobileNo(userProfileDto.getMobileNo());
             Optional<Branches> branches = branchRepo.findById(userProfileDto.getBranchId());
             if(branches.isPresent()) {
                 studentExist.get().setBranches(branches.get());
             }
            studentExist.get().setSection(userProfileDto.getSection());
            studentExist.get().setYear(userProfileDto.getYear());
            studentExist.get().setSemester(userProfileDto.getSemester());

            studentRepo.save(studentExist.get());

            sb.append(messageSource.getMessage("User.updated", null, LocaleContextHolder.getLocale()));
        } else {
            throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
        }
        return sb.toString();
    }

    @Transactional
    @Modifying
    public String updateEmployee(UpdateStudentProfileDto userProfileDto, String username) {
        Optional<Employee> employeeExist = Optional.ofNullable(employeeRepo.findByEmail(username));

        StringBuilder sb = new StringBuilder();
        if (employeeExist.isPresent()) {
            employeeExist.get().setFirstName(userProfileDto.getFirstName());
            employeeExist.get().setLastName(userProfileDto.getLastName());
            employeeExist.get().setMobileNo(userProfileDto.getMobileNo());

            employeeRepo.save(employeeExist.get());

            sb.append(messageSource.getMessage("User.updated", null, LocaleContextHolder.getLocale()));
        } else {
            throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
        }
        return sb.toString();
    }

    @Transactional
    @Modifying
    public String addAddress(AddressDto addressDto, String username) {
        Optional<User> user = Optional.ofNullable(userRepo.findByEmail(username));

        StringBuilder sb = new StringBuilder();
        if (user.isPresent()) {
            addressDto.setUserId(user.get().getUserId());
            Address address = new Address();
            BeanUtils.copyProperties(addressDto, address);

            address.setDeleted(false);
            addressRepo.save(address);

            sb.append(messageSource.getMessage("Address.added", null, LocaleContextHolder.getLocale()));

        } else {
            throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
        }
        return sb.toString();
    }

    @Transactional
    @Modifying
    public String deleteAddress(Long addressId) {
        Optional<Address> address = addressRepo.findById(addressId);

        StringBuilder sb = new StringBuilder();
        if (address.isPresent()) {
            address.get().setDeleted(true);
            addressRepo.save(address.get());
            sb.append(messageSource.getMessage("Address.deleted", null, LocaleContextHolder.getLocale()));
        } else {
            throw new NotFoundException(messageSource.getMessage("NotFound.address", null, LocaleContextHolder.getLocale()));
        }
        return sb.toString();
    }

    @Transactional
    @Modifying
    public String updateAddress(AddressDto addressDto, Long addressId, String username) {

        Optional<User> user = Optional.ofNullable(userRepo.findByEmail(username));

        if (user.isPresent()) {
            Optional<Address> addressExist = addressRepo.findById(addressId);
            StringBuilder sb = new StringBuilder();

            addressDto.setUserId(user.get().getUserId());
            addressDto.setId(addressId);
            if (addressExist.isPresent()) {
                BeanUtils.copyProperties(addressDto, addressExist.get());

                addressRepo.save(addressExist.get());

                sb.append(messageSource.getMessage("Address.updated", null, LocaleContextHolder.getLocale()));
            } else {
                throw new NotFoundException(messageSource.getMessage("NotFound.address", null, LocaleContextHolder.getLocale()));
            }
            return sb.toString();
        } else {
            throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
        }

    }
}
