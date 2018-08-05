package com.tompy.item;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.item.api.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ItemTest {
    public ItemFactory itemFactory;

    @Mock
    private Adventure mockAdventure;

    @Before
    public void init() {
        itemFactory = new ItemFactoryImpl(mockAdventure);
    }

    @Test
    public void testName() {
        Item i = itemFactory.create(0L, "item", "long lost item", "An ancient grail");
        assertTrue(i.getName().equals("long lost item"));
        assertTrue(i.getShortName().equals("item"));
    }

    @Test
    public void testDescriptors() {
        Item i = itemFactory.create(0L, "item", "long lost item", "An ancient grail");
        assertTrue(i.getDescriptionWords().get(0).equals("long"));
        assertTrue(i.getDescriptionWords().get(1).equals("lost"));
        assertTrue(i.getDescriptionWords().size() == 2);
    }

    @Test
    public void getDetailDescription() {
        Item i = itemFactory.create(0L, "item", "long lost item", "An ancient grail");
        assertTrue(i.getDetailDescription().equals("An ancient grail"));
    }
}
