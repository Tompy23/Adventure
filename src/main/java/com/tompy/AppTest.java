package com.tompy;

import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.Entity;
import com.tompy.entity.item.api.ItemBuilder;
import com.tompy.entity.item.interna.ItemImpl;
import com.tompy.io.UserInput;
import com.tompy.io.UserInputTextImpl;

import java.util.ArrayList;
import java.util.List;

public class AppTest {

    public static void main(String[] args) {
        AppTest a = new AppTest();
        System.exit(a.go(args));
    }

    public int go(String[] args) {
        // Test 1 - Entity Selection
        UserInput io = new UserInputTextImpl();

        List<Entity> items = buildItems();

        Long choice = EntityUtil.findEntityByDescription(items, "cookie", io);

        for (Entity entity : items) {
            if (choice == entity.getKey()) {
                System.out.println(entity.getShortName() + ", " + entity.getDetailDescription());
            }
        }

        return 0;
    }

    private List<Entity> buildItems() {
        List<Entity> items = new ArrayList<>();
        ItemBuilder itemBuilder = ItemImpl.createBuilder(1L, null);
        items.add(itemBuilder.name("cookie").longName("chocolate chip cookie").description("chips ahoy!").build());
        itemBuilder = ItemImpl.createBuilder(2L, null);
        items.add(itemBuilder.name("cookie").longName("peanute butter cookie").description("my favorite!").build());
        itemBuilder = ItemImpl.createBuilder(3L, null);
        items.add(itemBuilder.name("brownie").longName("chocolate brownie").description("special brownies!").build());

        return items;
    }
}
