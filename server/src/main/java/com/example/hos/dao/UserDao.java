//package com.example.hos.dao;
//
//import com.dbc.workload.model.entity.*;
//import com.dbc.workload.model.ro.EmpReportRo;
//import com.dbc.workload.model.vo.EmpWorkHourVo;
//import com.dbc.workload.util.Constant;
//import com.dragonsoftbravo.jpadsl.JPADslBaseDao;
//import com.dragonsoftbravo.jpadsl.PredicateBuilder;
//import com.querydsl.core.types.OrderSpecifier;
//import com.querydsl.core.types.Projections;
//import com.querydsl.core.types.dsl.ComparableExpressionBase;
//import com.querydsl.jpa.impl.JPAQuery;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static com.dragonsoftbravo.jpadsl.PredicateBuilder.buildQ;
//
///**
// * @author yangguang.hu
// * @create 2020-09-28 10:47
// */
//@Repository
//public class EmployeeDao extends JPADslBaseDao<Employee, String> {
//
//    @Autowired
//    public EmployeeDao(EntityManager entityManager) {
//        setEntityManager(entityManager);
//    }
//
//    @Override
//    public QEmployee basePath() {
//        return QEmployee.employee;
//    }
//
//    /**
//     * 查询员工
//     * @param
//     * @return
//     * @author Qingcheng Wang
//     * @date 2020/11/17
//     */
//    public List<Employee> findEmployees(String roleName, String id, String employeeName) {
//        PredicateBuilder pb = buildQ().eq(basePath().deleted, false);
//        pb.eq(basePath().employeeId, id);
//        Optional.ofNullable(roleName).filter(StringUtils::isNotBlank).ifPresent(r -> {
//            PredicateBuilder subBuilder = buildQ().eq(basePath().employeeId, QEmployeeRole.employeeRole.empId);
//            subBuilder.and(buildQ().eq(QEmployeeRole.employeeRole.role.roleName, roleName));
//            pb.exists(subBuilder, QEmployeeRole.employeeRole);
//        });
//        pb.like(basePath().empName, employeeName);
//        return query().from(basePath()).where(pb.get()).orderBy(basePath().empName.asc()).fetch();
//    }
//
//    /**
//     * 查询时间段内生效的项目的成员
//     *
//     * @param
//     * @return
//     * @author Qingcheng Wang
//     * @date 2020/11/18
//     */
//    public List<Employee> findHasProjectEmployees(Date startDate, Date endDate) {
//        PredicateBuilder pb = buildQ().eq(basePath().deleted, false);
//        PredicateBuilder subBuilder = buildQ().eq(basePath().employeeId, QEmployeeProject.employeeProject.employeeId);
//        subBuilder.and(buildQ().lt(QEmployeeProject.employeeProject.project.projectStartTime, endDate));
//        subBuilder.and(buildQ().gt(QEmployeeProject.employeeProject.project.projectEndTime, startDate));
//        pb.exists(subBuilder, QEmployeeProject.employeeProject);
//        return query().from(basePath()).where(pb.get()).orderBy(basePath().empName.asc()).fetch();
//    }
//
//    /**
//     * 员工工时分页
//     * @param
//     * @return
//     * @author Qingcheng Wang
//     * @date 2020/11/19
//     */
//    public Page<EmpWorkHourVo> empWorkHourPageByTime(EmpReportRo empReportRo) {
//        PredicateBuilder pb = buildQ().eq(basePath().deleted, false);
//        PredicateBuilder subBuilder = buildQ().eq(basePath().employeeId, QEmployeeProject.employeeProject.employeeId);
//        subBuilder.and(buildQ().lt(QEmployeeProject.employeeProject.project.projectStartTime, empReportRo.getProjectEndTime()));
//        subBuilder.and(buildQ().gt(QEmployeeProject.employeeProject.project.projectEndTime, empReportRo.getProjectStartTime()));
//        subBuilder.and(buildQ().eq(QEmployeeProject.employeeProject.project.deleted, false));
//        pb.exists(subBuilder, QEmployeeProject.employeeProject);
//
//        JPAQuery<EmpWorkHourVo> query = queryFactory().select(Projections.bean(EmpWorkHourVo.class, basePath().employeeId, basePath().empName.as("employeeName"), QWorkHour.workHour.workHours.sum().as("workHours")))
//                .from(basePath()).leftJoin(QWorkHour.workHour).on(basePath().employeeId.eq(QWorkHour.workHour.employeeId)).where(pb.get()).groupBy(basePath().employeeId);
//
//        return selectPageable(query, empReportRo.pageable(), makeOrder(empReportRo.getOrderBy(), empReportRo.getOrder()));
//    }
//
//    /**
//     * 排序结果
//     *
//     * @param
//     * @return
//     * @author Qingcheng Wang
//     * @date 2020/11/19
//     */
//    @SuppressWarnings("rawtypes")
//    private OrderSpecifier makeOrder(String orderName, String order) {
//        ComparableExpressionBase comparableExpressionBase = null;
//        if (StringUtils.equals(orderName, Constant.DataTableFiled.EMP_NAME)) {
//            comparableExpressionBase = basePath().empName;
//        } else {
//            comparableExpressionBase = QWorkHour.workHour.workHours.sum();
//        }
//        if (StringUtils.isBlank(order) || Sort.Direction.ASC == Sort.Direction.fromString(order)) {
//            return comparableExpressionBase.asc().nullsFirst();
//        } else {
//            return comparableExpressionBase.desc().nullsLast();
//        }
//    }
//}
