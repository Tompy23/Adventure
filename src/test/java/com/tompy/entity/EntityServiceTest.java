package com.tompy.entity;

import com.tompy.attribute.api.Attribute;
import com.tompy.attribute.api.AttributeManager;
import com.tompy.attribute.api.AttributeManagerFactory;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.internal.EntityServiceImpl;
import com.tompy.entity.item.api.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;
import java.util.OptionalInt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class EntityServiceTest {
    @Mock
    AttributeManagerFactory mockAttributeManagerFactory;

    @Mock
    AttributeManager mockAttributeManager;

    @Mock
    ItemFactory mockItemFactory;

    @Mock
    Item mockItem;

    @Mock
    Map<Long, AttributeManager> mockAttributeManagers;

    private EntityService entityService;

    @Before
    public void init() {
        when(mockAttributeManagerFactory.createMap()).thenReturn(mockAttributeManagers);
        entityService = new EntityServiceImpl(mockAttributeManagerFactory, mockItemFactory);
    }

    @Test
    public void testCreateItem() {
        when(mockItemFactory.create(anyLong(), anyString(), anyString(), anyString())).thenReturn(mockItem);
        Item i = entityService.createItem("name", "longName", "description");
        verify(mockItemFactory, times(1)).create(anyLong(), anyString(), anyString(), anyString());
    }

    @Test
    public void testAddAttribute() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(true);
        when(mockAttributeManagers.get(any())).thenReturn(mockAttributeManager);
        entityService.add(mockItem, Attribute.TEST_NORMALA);
        verify(mockAttributeManager, times(1)).add(any());
    }


    @Test
    public void testAddAttributeNone() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(false);
        entityService.add(mockItem, Attribute.TEST_NORMALA);
        verify(mockAttributeManager, times(0)).add(any());
    }

    @Test
    public void testAddAttributeValue() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(true);
        when(mockAttributeManagers.get(any())).thenReturn(mockAttributeManager);
        entityService.add(mockItem, Attribute.TEST_NORMALA, 1);
        verify(mockAttributeManager, times(1)).add(any(), any());
    }


    @Test
    public void testAddAttributeValueNone() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(false);
        entityService.add(mockItem, Attribute.TEST_NORMALA, 1);
        verify(mockAttributeManager, times(0)).add(any(), any());
    }

    @Test
    public void testRemoveAttribute() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(true);
        when(mockAttributeManagers.get(any())).thenReturn(mockAttributeManager);
        entityService.remove(mockItem, Attribute.TEST_NORMALA);
        verify(mockAttributeManager, times(1)).remove(any());
    }


    @Test
    public void testRemoveAttributeNone() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(false);
        entityService.remove(mockItem, Attribute.TEST_NORMALA);
        verify(mockAttributeManager, times(0)).remove(any());
    }

    @Test
    public void testResetAttribute() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(true);
        when(mockAttributeManagers.get(any())).thenReturn(mockAttributeManager);
        entityService.reset(mockItem, Attribute.TEST_NORMALA);
        verify(mockAttributeManager, times(1)).reset(any());
    }


    @Test
    public void testResetAttributeNone() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(false);
        entityService.reset(mockItem, Attribute.TEST_NORMALA);
        verify(mockAttributeManager, times(0)).reset(any());
    }

    @Test
    public void testIsAttribute() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(true);
        when(mockAttributeManagers.get(any())).thenReturn(mockAttributeManager);
        when(mockAttributeManager.is(any())).thenReturn(true);
        boolean check = entityService.is(mockItem, Attribute.TEST_NORMALA);
        assertTrue(check);
    }

    @Test
    public void testIsFalseAttribute() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(true);
        when(mockAttributeManagers.get(any())).thenReturn(mockAttributeManager);
        when(mockAttributeManager.is(any())).thenReturn(false);
        boolean check = entityService.is(mockItem, Attribute.TEST_NORMALA);
        assertFalse(check);
    }

    @Test
    public void testIsAttributeNone() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(false);
        boolean check = entityService.is(mockItem, Attribute.TEST_NORMALA);
        assertFalse(check);
    }

    @Test
    public void testValueAttribute() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(true);
        when(mockAttributeManagers.get(any())).thenReturn(mockAttributeManager);
        when(mockAttributeManager.getValue(any())).thenReturn(OptionalInt.of(5));
        OptionalInt check = entityService.valueFor(mockItem, Attribute.TEST_NORMALA);
        assertTrue(check.getAsInt() == 5);
    }

    @Test
    public void testNoValueAttribute() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(true);
        when(mockAttributeManagers.get(any())).thenReturn(mockAttributeManager);
        when(mockAttributeManager.getValue(any())).thenReturn(OptionalInt.empty());
        OptionalInt check = entityService.valueFor(mockItem, Attribute.TEST_NORMALA);
        assertTrue(check.equals(OptionalInt.empty()));
    }

    @Test
    public void testValueAttributeNone() {
        when(mockAttributeManagers.containsKey(any())).thenReturn(false);
        OptionalInt check = entityService.valueFor(mockItem, Attribute.TEST_NORMALA);
        assertTrue(check.equals(OptionalInt.empty()));
    }
}
