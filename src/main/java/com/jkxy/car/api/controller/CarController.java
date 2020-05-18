package com.jkxy.car.api.controller;

import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.service.CarService;
import com.jkxy.car.api.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * carSys
 *
 * @author s7xjna
 * @creat 2020-05-16-16:59
 */

@RestController
@RequestMapping("car")
public class CarController {
    @Autowired
    private CarService carService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findAll")
    public JSONResult findAll() {
        List<Car> cars = carService.findAll();
        return JSONResult.ok(cars);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("findById/{id}")
    public JSONResult findById(@PathVariable int id) {
        Car car = carService.findById(id);
        return JSONResult.ok(car);
    }

    /**
     * 通过车名查询
     *
     * @param carName
     * @return
     */
    @GetMapping("findByCarName/{carName}")
    public JSONResult findByCarName(@PathVariable String carName) {
        List<Car> cars = carService.findByCarName(carName);
        return JSONResult.ok(cars);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById/{id}")
    public JSONResult deleteById(@PathVariable int id) {
        carService.deleteById(id);
        return JSONResult.ok();
    }

    /**
     * 通过id更新全部信息
     *
     * @return
     */
    @PostMapping("updateById")
    public JSONResult updateById(Car car) {
        carService.updateById(car);
        return JSONResult.ok();
    }

    /**
     * 通过id增加
     *
     * @param car
     * @return
     */
    @PostMapping("insertCar")
    public JSONResult insertCar(Car car) {
        carService.insertCar(car);
        return JSONResult.ok();
    }

    /**
     * 要买车啦
     *
     * @param carName,carSeries,inventory
     * @return
     */

    @PostMapping("purchaseCar/{carName}/{carSeries}/{inventory}")
    public JSONResult purchaseCar(
            @PathVariable("carName") String carName,
            @PathVariable("carSeries") String carSeries,
            @PathVariable("inventory") Integer inventory) {

        //首先查询客户要购买车型的库存余量；
        Car car = carService.selectByCarNameAndCarSeries(carName, carSeries);

        //如果需求大于库存，就不让你买；
        if (car.getInventory() < inventory) {
            return JSONResult.errorException("库存不足，暂不支持购买");
        } else {
            //否则就正常卖车，即对库存进行更新；
            int newInventory = car.getInventory() - inventory;
            car.setInventory(newInventory);
            carService.updateById(car);
            return JSONResult.ok();
        }
    }

    /**
     * 分页模糊查询
     *
     * @param carName 车名
     * @param page    页数
     * @param size    每页数目
     * @return
     */
    @GetMapping("findByWhere/{carName}/{page}/{size}")
    public JSONResult findByWhere(@PathVariable String carName,
                                  @PathVariable Integer page,
                                  @PathVariable Integer size) {
        if (page >= 1) {
            List<Car> cars = carService.findCarInfoByWhere(carName, (page - 1) * size, size);
            return JSONResult.ok(cars);
        } else {
            return JSONResult.errorMsg("页数不能小于1");
        }
    }

}
