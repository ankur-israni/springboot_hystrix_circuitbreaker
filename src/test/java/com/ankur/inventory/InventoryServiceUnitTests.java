package com.ankur.inventory;


import com.ankur.inventory.domain.*;
import com.ankur.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryServiceUnitTests {

    private InventoryService inventoryService;

    @Test
    public void findById() {
        InventoryService service = new InventoryService();
        ResponseEntity<InventoryFindByIdResponse> response = (ResponseEntity<InventoryFindByIdResponse>) service.findById(new InventoryFindByIdRequest(1));
        assertEquals("Laptop",response.getBody().getItem().getName());
    }

    @Test
    public void findByName() {
        InventoryService service = new InventoryService();
        ResponseEntity<InventoryFindByNameResponse> response = (ResponseEntity<InventoryFindByNameResponse>) service.findByName(new InventoryFindByNameRequest(("Lamp")));
        Collection<Item> items =  response.getBody().getItems();
        items.forEach(item->{
            assertTrue(item.getName().contains("Lamp"));
        });
    }


}
