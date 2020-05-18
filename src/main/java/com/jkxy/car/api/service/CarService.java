package com.jkxy.car.api.service;

import com.jkxy.car.api.pojo.Car;

import java.util.List;


public interface CarService {

    List<Car> findAll();

    Car findById(int id);

    List<Car> findByCarName(String carName);

    void deleteById(int id);

    void updateById(Car car);

    void insertCar(Car car);

    //查询客户要购买车型的库存余量；
    Car selectByCarNameAndCarSeries(String carName, String carSeries);

    List<Car> findCarInfoByWhere(String carName, Integer page, Integer size);
}
