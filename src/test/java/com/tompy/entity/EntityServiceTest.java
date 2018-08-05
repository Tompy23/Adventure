package com.tompy.entity;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.attribute.api.AttributeManagerFactory;
import com.tompy.attribute.internal.AttributeManagerFactoryImpl;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.internal.EntityServiceImpl;
import com.tompy.entity.item.api.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.OptionalInt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class EntityServiceTest {
    @Mock
    private Adventure mockAdventure;

    private AttributeManagerFactory attributeManagerFactory;
    private EntityService entityService;

    @Before
    public void init() {
        attributeManagerFactory = new AttributeManagerFactoryImpl(mockAdventure);
        entityService = new EntityServiceImpl(attributeManagerFactory);
    }

    @Test
    public void testCreateItem() {
        Item i = entityService.createItemBuilder().name("item").longName("my item").description("stuff").build();
        assertTrue(i.getKey() == 1);
    }

    @Test
    public void testAddAttribute() {
        Item i = entityService.createItemBuilder().name("item").longName("my item").description("stuff").build();
        entityService.add(i, Attribute.TEST_NORMALA);
        assertTrue(entityService.is(i, Attribute.TEST_NORMALA));
    }

    @Test
    public void testAddAttributeValue() {
        Item i = entityService.createItemBuilder().name("item").longName("my item").description("stuff").build();
        entityService.add(i, Attribute.TEST_HAS_VALUE, 1);
    }

    @Test
    public void testRemoveAttribute() {
        Item i = entityService.createItemBuilder().name("item").longName("my item").description("stuff").build();
        entityService.add(i, Attribute.TEST_NORMALA);
        entityService.remove(i, Attribute.TEST_NORMALA);
    }

    @Test
    public void testResetAttribute() {
        Item i = entityService.createItemBuilder().name("item").longName("my item").description("stuff").build();
        entityService.add(i, Attribute.TEST_NORMALA);
        entityService.reset(i, Attribute.TEST_NORMALA);
    }

    @Test
    public void testIsAttribute() {
        Item i = entityService.createItemBuilder().name("item").longName("my item").description("stuff").build();
        entityService.add(i, Attribute.TEST_NORMALA);
        boolean check = entityService.is(i, Attribute.TEST_NORMALA);
        assertTrue(check);
    }

    @Test
    public void testIsFalseAttribute() {
        Item i = entityService.createItemBuilder().name("item").longName("my item").description("stuff").build();
        entityService.add(i, Attribute.TEST_NORMALA);
        boolean check = entityService.is(i, Attribute.TEST_NORMALB);
        assertFalse(check);
    }

    @Test
    public void testValueAttribute() {
        Item i = entityService.createItemBuilder().name("item").longName("my item").description("stuff").build();
        entityService.add(i, Attribute.TEST_HAS_VALUE, 5);
        OptionalInt check = entityService.valueFor(i, Attribute.TEST_HAS_VALUE);
        assertTrue(check.getAsInt() == 5);
    }

    @Test
    public void testNoValueAttribute() {
        Item i = entityService.createItemBuilder().name("item").longName("my item").description("stuff").build();
        entityService.reset(i, Attribute.TEST_NORMALA);
        OptionalInt check = entityService.valueFor(i, Attribute.TEST_NORMALA);
        assertTrue(check.equals(OptionalInt.empty()));
    }

}
