package org.example.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class AthleteData {

    @CsvBindByName(column = "session")
    private String session;

    @CsvBindByName(column = "task")
    private String task;

    @CsvBindByName(column = "date")
    private String date;

    @CsvBindByName(column = "position")
    private String position;

    @CsvBindByName(column = "dorsal")
    private String dorsal;

    @CsvBindByName(column = "player")
    private String player;

    @CsvBindByName(column = "total_distance")
    private double totalDistance;

    @CsvBindByName(column = "minute_distance")
    private double minuteDistance;

    @CsvBindByName(column = "num_dec_expl")
    private double numDecExpl;

    @CsvBindByName(column = "num_acc_expl")
    private double numAccExpl;

    @CsvBindByName(column = "distance_vrange2")
    private double distanceVrange2;

    @CsvBindByName(column = "distance_vrange3")
    private double distanceVrange3;

    @CsvBindByName(column = "distance_vrange1")
    private double distanceVrange1;

    @CsvBindByName(column = "player_load")
    private double playerLoad;

    @CsvBindByName(column = "hr_max")
    private double hrMax;

    @CsvBindByName(column = "hid_intervals")
    private double hidIntervals;

    @CsvBindByName(column = "max_acc")
    private double maxAcc;

    @CsvBindByName(column = "hsr")
    private double hsr;

    @CsvBindByName(column = "max_dec")
    private double maxDec;

    @CsvBindByName(column = "max_speed")
    private double maxSpeed;

    @CsvBindByName(column = "distance_vrange6")
    private double distanceVrange6;

    @CsvBindByName(column = "average_speed")
    private double averageSpeed;

    @CsvBindByName(column = "num_acc")
    private double numAcc;

    @CsvBindByName(column = "hmld_time")
    private double hmldTime;

    @CsvBindByName(column = "hmld")
    private double hmld;

    @CsvBindByName(column = "hmld_count")
    private double hmldCount;

    @CsvBindByName(column = "num_hids")
    private double numHids;

    @CsvBindByName(column = "num_dec")
    private double numDec;

    @CsvBindByName(column = "distance_vrange4")
    private double distanceVrange4;

    @CsvBindByName(column = "distance_vrange5")
    private double distanceVrange5;

    @CsvBindByName(column = "sprints")
    private double sprints;

    @CsvBindByName(column = "zone1_hsr_distance")
    private double zone1HsrDistance;

    @CsvBindByName(column = "num_hsr")
    private double numHsr;

    @CsvBindByName(column = "zone2_hsr_distance")
    private double zone2HsrDistance;

    @CsvBindByName(column = "zone4_hsr_distance")
    private double zone4HsrDistance;
}
