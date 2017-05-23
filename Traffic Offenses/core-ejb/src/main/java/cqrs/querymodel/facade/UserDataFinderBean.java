//package cqrs.querymodel.facade;
//
//import api.exception.UserNotActiveException;
//import api.exception.repository.SnapshotNotFoundException;
//import api.exception.repository.UserNotFoundException;
//import api.readmodel.UserDataFinder;
//import cqrs.querymodel.utility.VisualObjectDataTransfer;
//import cqrs.querymodel.utility.PointsCalculator;
//import cqrsmodel.CompleteUserInformationVO;
//import domainmodel.domain.event.Event;
//import domainmodel.domain.snapshot.Snapshot;
//import domainmodel.domain.user.User;
//import infrastructure.repository.api.SnapshotRepository;
//import infrastructure.repository.api.UserRepository;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import java.util.List;
//
//@Stateless
//public class UserDataFinderBean implements UserDataFinder {
//
//    @EJB
//    private UserRepository userRepository;
//
//    @EJB
//    private EventFinderBean eventFinderManagerBean;
//
//    @EJB
//    private SnapshotRepository snapshotRepository;
//
//
//
//    public UserInformationVO findUserInformationForPoliceman(String pesel, String drivingLicense) throws UserNotFoundException,
//                                                                        UserNotActiveException{
//        User user = userRepository.findUserByPeselAndDrivingLicenseNumber(pesel, drivingLicense);
//        Long userId = user.getAggregateRootEntityId();
//
//        try {
//            Snapshot snapshot = snapshotRepository.findLastSnapshot(userId);
//            List<Event> events = eventFinderManagerBean.findEventsWithSnapshot(userId, snapshot.getEventVersion());
//            return VisualObjectDataTransfer.transferUserInformationForPoliceman(user, snapshot, events);
//
//        } catch (SnapshotNotFoundException e) {
//            List<Event> events = eventFinderManagerBean.findEventsWithoutSnapshot(userId);
//            numberOfPoints = PointsCalculator.calculateUserPoints(events, 0);
//            return VisualObjectDataTransfer.transferUserInformationForPoliceman(user, numberOfPoints);
//        }
//
//    }
//
//
//
//    @Override
//    public CompleteUserInformationVO findCompleteUserDataForOwner(String pesel) throws UserNotFoundException, UserNotActiveException {
//        User user = userRepository.findUserByPesel(pesel);
//        Long userId = user.getAggregateRootEntityId();
//        int numberOfPoints;
//        List<Event> events;
//
//        try {
//            Snapshot snapshot = snapshotRepository.findLastSnapshot(userId);
//            events = eventFinderManagerBean.findEventsWithSnapshot(userId, snapshot.getEventVersion());
//            numberOfPoints = PointsCalculator.calculateUserPoints(events, snapshot.getNumberOfPoints());
//        } catch (SnapshotNotFoundException e) {
//            events = eventFinderManagerBean.findEventsWithoutSnapshot(userId);
//            numberOfPoints = PointsCalculator.calculateUserPoints(events, 0);
//        }
//        return VisualObjectDataTransfer.tra(user, numberOfPoints);
//    }
//
//
//
//
//
//}
