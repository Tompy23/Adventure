package com.tompy;

import com.tompy.attribute.internal.AttributeManagerFactoryImpl;
import com.tompy.directive.ItemType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.internal.EntityServiceImpl;
import com.tompy.entity.item.api.Item;
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
        EntityService entityService = new EntityServiceImpl(new AttributeManagerFactoryImpl());
        UserInput io = new UserInputTextImpl(System.in, System.out, entityService);

        List<Item> items = buildItems(entityService);

        Long key = EntityUtil.findEntityByDescription(items, "cookie", io);

        Item choice = items.stream().filter((i) -> i.getKey().equals(key)).findFirst().get();

        if (choice != null) {
            System.out.println(choice.getShortName() + ", " + choice.getDetailDescription());
        } else {
            System.out.println("No proper choice made.");
        }

        return 0;
    }

    private List<Item> buildItems(EntityService entityService) {
        List<Item> items = new ArrayList<>();
        ItemBuilder itemBuilder = ItemImpl.createBuilder(1L, entityService);
        items.add(itemBuilder.name("cookie").longName("chocolate chip cookie").description("chips ahoy!")
                      .type(ItemType.ITEM_TEST).build());
        itemBuilder = ItemImpl.createBuilder(2L, entityService);
        items.add(itemBuilder.name("cookie").longName("peanute butter cookie").description("my favorite!")
                      .type(ItemType.ITEM_TEST).build());
        itemBuilder = ItemImpl.createBuilder(3L, entityService);
        items.add(itemBuilder.name("brownie").longName("chocolate brownie").description("special brownies!")
                      .type(ItemType.ITEM_TEST).build());

        return items;
    }
}
