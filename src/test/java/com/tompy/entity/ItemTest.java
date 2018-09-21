package com.tompy.entity;

import com.tompy.attribute.api.AttributeManagerFactory;
import com.tompy.directive.ItemType;
import com.tompy.entity.event.api.EventManagerFactory;
import com.tompy.entity.internal.EntityServiceImpl;
import com.tompy.entity.item.api.Item;
import com.tompy.entity.item.api.ItemBuilder;
import com.tompy.entity.item.api.ItemBuilderFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ItemTest {
    public ItemBuilderFactory itemBuilderFactory;

    @Mock
    private AttributeManagerFactory mockAttributeManagerFactory;

    @Mock
    private EventManagerFactory mockEventManagerFactory;

    @Before
    public void init() {
        itemBuilderFactory = new EntityServiceImpl(mockAttributeManagerFactory, mockEventManagerFactory);
    }

    @Test
    public void testName() {
        ItemBuilder builder = itemBuilderFactory.createItemBuilder();
        Item i = builder.type(ItemType.ITEM_TEST).name("item").longName("long lost item").description(
                "An ancient grail").build();
        assertTrue(i.getName().equals("long lost item"));
        assertTrue(i.getName().equals("item"));
    }

    @Test
    public void testDescriptors() {
        ItemBuilder builder = itemBuilderFactory.createItemBuilder();
        Item i = builder.type(ItemType.ITEM_TEST).name("item").longName("long lost item").description(
                "An ancient grail").build();
        assertTrue(i.getDescriptionWords().get(0).equals("long"));
        assertTrue(i.getDescriptionWords().get(1).equals("lost"));
        assertTrue(i.getDescriptionWords().size() == 2);
    }

    @Test
    public void getDetailDescription() {
        ItemBuilder builder = itemBuilderFactory.createItemBuilder();
        Item i = builder.type(ItemType.ITEM_TEST).name("item").longName("long lost item").description(
                "An ancient grail").build();
        assertTrue(i.getDescription().equals("An ancient grail"));
    }
}
