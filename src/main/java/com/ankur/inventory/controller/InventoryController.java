package com.ankur.inventory.controller;

import com.ankur.inventory.domain.*;
import com.ankur.inventory.service.InventoryService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/inventory/services")
@Api(value = "/inventory/services", tags = ("Inventory Management"))
@CrossOrigin(allowedHeaders = "*",maxAge = 3600)
public class InventoryController {

    private static final String CLIENT_ID = "client-id";
    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RequestMapping(value = "listall", method = {RequestMethod.GET}, produces = "application/json")
    @ApiOperation(value = "listall", notes = "Get all inventory items", nickname = "listall")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
            @ApiResponse(code = 200, message = "Success", response = InventoryListAllResponse.class),
            @ApiResponse(code = 503, message = "Service Unavailaible", response = ServiceErrorResponse.class)
    })
    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    public ResponseEntity<?> listall(@RequestHeader(value = CLIENT_ID) String clientId
    ) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return inventoryService.listAll();
    }

    public ResponseEntity<?> fallback(String msg) {
        System.out.println("msg from fallback ->"+msg);
        ServiceErrorResponse apiError = new ServiceErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "5003", "Netflix Hystrix > Fallback method invoked");
        return new ResponseEntity<ServiceErrorResponse>(apiError, (HttpStatus) apiError.getHttpStatus());
    }


    @RequestMapping(value = "findById", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "findById", notes = "Find a single inventory item by id", nickname = "findById")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
            @ApiResponse(code = 200, message = "Success", response = InventoryFindByIdResponse.class)})
    public ResponseEntity<?> findById(@RequestHeader(value = CLIENT_ID) String clientId,
                                                 @Valid @RequestBody InventoryFindByIdRequest request) {
        return inventoryService.findById(request);
    }




}

