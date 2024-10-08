package com.team2.finalproject.domain.dispatch.model.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DispatchUpdateResponse {

    @Schema(description = "오류 여부", example = "false")
    private boolean errorYn;
    @Schema(example = "30.5", description = "주행거리 (km)")
    private Double mileage;  // 주행거리 (km)
    @Schema(example = "34", description = "주행시간 (분)")
    private Long totalTime; // 주행시간 (분)
    @Schema(example = "14:00:00", description = "휴식시작시간")
    private LocalTime breakStartTime;
    @Schema(example = "15:00:00", description = "휴식종료시간")
    private LocalTime breakEndTime;
    @Schema(example = "3", description = "휴식경유지(해당경유지로 이동중)")
    private int restingStopover;
    @Schema(example = "20", description = "전체 주문 or 거리")
    private int totalOrderOrDistanceNum;
    @Schema(description = "모든 배차의 용적률", example = "90")
    private int totalFloorAreaRatio;
    @Schema(description = "배차 하나의 용적률", example = "85")
    private int floorAreaRatio;
    @Schema(example = "80", description = "가용 주문")
    private int availableNum;
    @Schema(example = "지입", description = "배송 유형")
    private String contractType;
    private StartStopover startStopover;
    private List<DispatchDetailResponse> dispatchDetailList;
    @ArraySchema(
            arraySchema = @Schema(description = "경로 좌표 리스트"),
            schema = @Schema(
                    example = "{\"lat\": 37.5665, \"lon\": 126.9780}",
                    description = "경로 좌표"
            )
    )
    private List<Map<String, Double>> coordinates;

    private DispatchUpdateResponse(
            Double mileage,
            Long totalTime,
            LocalTime breakStartTime,
            LocalTime breakEndTime,
            int restingStopover,
            int totalOrderOrDistanceNum,
            int totalFloorAreaRatio,
            int floorAreaRatio,
            int availableNum,
            String contractType,
            StartStopover startStopover,
            List<DispatchDetailResponse> dispatchDetailList,
            List<Map<String, Double>> coordinates) {
        if (dispatchDetailList == null || dispatchDetailList.isEmpty()){
            this.errorYn = false;
        }else{
            this.errorYn = dispatchDetailList.stream().anyMatch((dispatchDetailResponse ->
                dispatchDetailResponse.isEntryRestricted()
                    || dispatchDetailResponse.isDelayRequestTime()
                    || dispatchDetailResponse.isOverContractNum()
                    || dispatchDetailResponse.isOverFloorAreaRatio()));
        }
        this.mileage = mileage;
        this.totalTime = totalTime / 1000 / 60;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.restingStopover = restingStopover;
        this.totalOrderOrDistanceNum = totalOrderOrDistanceNum;
        this.totalFloorAreaRatio = totalFloorAreaRatio;
        this.floorAreaRatio = floorAreaRatio;
        this.availableNum = availableNum;
        this.contractType = contractType;
        this.startStopover = startStopover;
        this.dispatchDetailList = dispatchDetailList;
        this.coordinates = coordinates;
    }

    public static DispatchUpdateResponse of(
            Double mileage,
            Long totalTime,
            LocalTime breakStartTime,
            LocalTime breakEndTime,
            int restingStopover,
            int totalOrderOrDistanceNum,
            int totalFloorAreaRatio,
            int floorAreaRatio,
            int availableNum,
            String contractType,
            StartStopover startStopover,
            List<DispatchDetailResponse> dispatchDetailList,
            List<Map<String, Double>> coordinates) {
        return new DispatchUpdateResponse(mileage, totalTime, breakStartTime, breakEndTime, restingStopover,
                totalOrderOrDistanceNum, totalFloorAreaRatio, floorAreaRatio, availableNum, contractType, startStopover,
                dispatchDetailList, coordinates);
    }

    @Getter
    @NoArgsConstructor
    public static class StartStopover {

        @Schema(example = "서울시 강동구 천호동", description = "주소")
        private String address;
        @Schema(example = "37.5409", description = "위도")
        private Double lat;
        @Schema(example = "127.1263", description = "경도")
        private Double lon;
        @Schema(example = "60", description = "지연시간(분)")
        private int delayTime;
        @Schema(example = "2023-06-15T09:00:00", description = "운송 시작 시간")
        private LocalDateTime departureTime;

        private StartStopover(String address, Double lat, Double lon, int delayTime,
                              LocalDateTime departureTime) {
            this.address = address;
            this.lat = lat;
            this.lon = lon;
            this.delayTime = delayTime;
            this.departureTime = departureTime;

        }

        public static DispatchUpdateResponse.StartStopover of(String address, Double lat,
                                                              Double lon, int delayTime, LocalDateTime departureTime) {
            return new DispatchUpdateResponse.StartStopover(address, lat, lon, delayTime,
                    departureTime);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class DispatchDetailResponse {

        @Schema(example = "서울시 강동구 천호동", description = "주소")
        private String address;
        @Schema(example = "27", description = "예상 이동 시간 (분)")
        private Long ett;
        @Schema(example = "2023-06-15T10:27:00", description = "예상작업시작시간")
        private LocalDateTime expectationOperationStartTime;
        @Schema(example = "2023-06-15T10:57:00", description = "예상작업종료시간")
        private LocalDateTime expectationOperationEndTime;
        @Schema(example = "30", description = "예상작업시간")
        private int expectedServiceDuration;
        @Schema(example = "37.5409", description = "위도")
        private Double lat;
        @Schema(example = "127.1263", description = "경도")
        private Double lon;
        @Schema(example = "30.4", description = "이동거리 (km)")
        private Double distance;
        @Schema(example = "true", description = "희망 도착시간 및 도착일 지연 여부")
        private boolean delayRequestTime;
        @Schema(example = "true", description = "진입불가 톤 코드 오류")
        private boolean isEntryRestricted;
        @Schema(description = "계약 초과 위반 여부", example = "false")
        private boolean overContractNum;
        @Schema(description = "용적률 초과", example = "false")
        private boolean overFloorAreaRatio;

        private DispatchDetailResponse(String address, Long ett,
                                       LocalDateTime expectationOperationStartTime,
                                       LocalDateTime expectationOperationEndTime,
                                       int expectedServiceDuration, Double lat, Double lon, Double distance,
                                       boolean delayRequestTime, boolean isEntryRestricted, boolean overContractNum,
                                       boolean overFloorAreaRatio) {
            this.address = address;
            this.ett = ett;
            this.expectationOperationStartTime = expectationOperationStartTime;
            this.expectationOperationEndTime = expectationOperationEndTime;
            this.expectedServiceDuration = expectedServiceDuration;
            this.lat = lat;
            this.lon = lon;
            this.distance = distance;
            this.delayRequestTime = delayRequestTime;
            this.isEntryRestricted = isEntryRestricted;
            this.overContractNum = overContractNum;
            this.overFloorAreaRatio = overFloorAreaRatio;
        }

        public static DispatchDetailResponse of(String address, Long ett,
                                                LocalDateTime expectationOperationStartTime,
                                                LocalDateTime expectationOperationEndTime,
                                                int expectedServiceDuration, Double lat, Double lon,
                                                Double distanceTypeMeter,
                                                boolean delayRequestTime, boolean isEntryRestricted,
                                                boolean overContractNum, boolean overFloorAreaRatio) {
            return new DispatchDetailResponse(address, ett, expectationOperationStartTime,
                    expectationOperationEndTime, expectedServiceDuration, lat, lon,
                    distanceTypeMeter / 1000, delayRequestTime, isEntryRestricted, overContractNum, overFloorAreaRatio);
        }


    }
}
