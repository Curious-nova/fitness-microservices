package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    public ActivityResponse trackActivity(ActivityRequest request) {
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        return mapToResponse(savedActivity);
    }

    //This method will convert Activity to ActivityResponse
    private ActivityResponse mapToResponse(Activity activity) {
    ActivityResponse res = new ActivityResponse();
    res.setId(activity.getId());
    res.setUserId(activity.getUserId());
    res.setType(activity.getType());
    res.setDuration(activity.getDuration());
    res.setCaloriesBurned(activity.getCaloriesBurned());
    res.setStartTime(activity.getStartTime());
    res.setAdditionalMetrics(activity.getAdditionalMetrics());
    res.setCreatedAt(activity.getCreatedAt());
    res.setUpdatedAt(activity.getUpdatedAt());
    return res;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities  =  activityRepository.findByUserId(userId);
        return activities.stream().map(this::mapToResponse).collect(Collectors.toList());
    }


    public ActivityResponse getActivityById(String activityId) {
        return activityRepository.findById(activityId).map(this::mapToResponse).orElseThrow(() -> new RuntimeException("Activity Not found with Id: " + activityId));
    }
}
