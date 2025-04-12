package com.TredBase.Assessment.service.Impl;

import com.TredBase.Assessment.model.Parent;
import com.TredBase.Assessment.model.ParentChild;
import com.TredBase.Assessment.model.Student;
import com.TredBase.Assessment.repository.ParentChildRepository;
import com.TredBase.Assessment.repository.ParentRepository;
import com.TredBase.Assessment.repository.StudentRepository;
import com.TredBase.Assessment.service.PaymentChildService;
import com.TredBase.Assessment.vo.request.ChildRequest;
import com.TredBase.Assessment.vo.request.ParentChildRequest;
import com.TredBase.Assessment.vo.request.ParentRequest;
import com.TredBase.Assessment.vo.response.ParentChildResponse;
import com.TredBase.Assessment.vo.response.ServiceResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class ParentChildServiceImpl implements PaymentChildService {

    private static final Logger logger = Logger.getLogger(ParentChildServiceImpl.class.getName());

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentChildRepository parentChildRepository;

    @Override
    public ParentChildResponse saveParentChild(ParentChildRequest request) {
        ParentChildResponse response = new ParentChildResponse(ServiceResponse.ERROR, ServiceResponse.GENERAL_ERROR_MESSAGE);

        try {
            List<Parent> parents = new ArrayList<>();
            for(ParentRequest req : request.getParents()) {
                if (StringUtils.isBlank(req.getEmail())) {
                    return new ParentChildResponse(ServiceResponse.ERROR, "Invalid Email provided");
                }
                if (StringUtils.isBlank(req.getFullName())) {
                    return new ParentChildResponse(ServiceResponse.ERROR, "Invalid FullName provided");
                }
                if (StringUtils.isBlank(req.getPhoneNumber())) {
                    return new ParentChildResponse(ServiceResponse.ERROR, "Invalid PhoneNumber provided");
                }
                if (req.getParentBalance().compareTo(BigDecimal.ZERO) == 0) {
                    return new ParentChildResponse(ServiceResponse.ERROR, "Invalid Parent Balance provided");
                }

                Parent byEmail = parentRepository.findByEmail(req.getEmail());
                if(Objects.nonNull(byEmail)) {
                    return new ParentChildResponse(ServiceResponse.ERROR, String.format("Parent with this email %s already exists ", req.getEmail()));
                }

                Parent parent = new Parent();
                parent.setBalance(req.getParentBalance());
                parent.setCreatedOn(new Date());
                parent.setEmail(req.getEmail());
                parent.setFullName(req.getFullName());
                parent.setPhoneNumber(req.getPhoneNumber());
                parents.add(parent);
            }

            parents = parentRepository.saveAll(parents);
            List<Student> studentList = new ArrayList<>();
            for(ChildRequest req : request.getChildren()) {
                if(StringUtils.isBlank(req.getName())) {
                    return new ParentChildResponse(ServiceResponse.ERROR, "Invalid Student Name provided");
                }
                if(req.getBalance().compareTo(BigDecimal.ZERO) == 0) {
                    return new ParentChildResponse(ServiceResponse.ERROR, "Invalid Student Balance provided");
                }
                Student student = new Student();
                student.setBalance(req.getBalance());
                student.setFullName(req.getName());
                student.setCreatedOn(new Date());
                studentList.add(student);
            }
            studentList = studentRepository.saveAll(studentList);
            List<ParentChild> parentChildren = new ArrayList<>();
            for(Parent parent : parents) {
                for (Student student : studentList) {
                    ParentChild parentChild = new ParentChild();
                    parentChild.setParentId(parent.getId());
                    parentChild.setStudentId(student.getId());
                    parentChildren.add(parentChild);
                }
            }
            parentChildRepository.saveAll(parentChildren);
            logger.info("parent child request saved successfully");

            return new ParentChildResponse(ServiceResponse.SUCCESS, ServiceResponse.GENERAL_SUCCESS_MESSAGE);
        } catch (Exception e) {
            logger.info("Error occurred while saving parent child request " + e.getMessage());
            return response;
        }
    }
}
