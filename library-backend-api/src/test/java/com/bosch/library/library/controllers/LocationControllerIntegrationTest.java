package com.bosch.library.library.controllers;

import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.entities.criteria.LocationCriteria;
import com.bosch.library.library.entities.dto.LocationCreateDTO;
import com.bosch.library.library.entities.dto.LocationDTO;
import com.bosch.library.library.repositories.LocationRepository;
import com.bosch.library.library.repositories.SupplierRepository;
import com.bosch.library.library.repositories.specifications.LocationSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LocationControllerIntegrationTest {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private MockMvc mockMvc;

    private static final String DEFAULT_ADDRESS = "opalchenska 23";

    private static final Long MISSING_ID = 15L;

    @BeforeEach
    public void setUp() {
        final Location location1 = new Location(DEFAULT_ADDRESS);
        final Location location2 = new Location("vitoshka 14");
        final Supplier supplier = new Supplier("Ciela", "bookstore", 0);
        supplier.addLocation(location1);

        this.supplierRepository.save(supplier);
        this.locationRepository.saveAll(List.of(location1, location2));
    }

    @AfterEach
    public void emptyData() {
        this.locationRepository.deleteAll();
        this.supplierRepository.deleteAll();
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testGetAllLocations() throws Exception {
        // Prepare data
        final String requestUrl = "/api/locations";

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].address").value(DEFAULT_ADDRESS));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testGetLocationBySpecificAddress() throws Exception {
        // Prepare data
        final String requestUrl = "/api/locations?address=opalchenska 23";

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].address").value(DEFAULT_ADDRESS));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testGetAllLocationsBySpecificSupplier() throws Exception {
        // Prepare data
        final String requestUrl = "/api/locations?supplier=Ciela";

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].address").value(DEFAULT_ADDRESS));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testGetAllLocationsByInvalidSupplierReturnsEmptyList() throws Exception {
        // Prepare data
        final String requestUrl = "/api/locations?supplier=Bookpoint";

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testCreateLocation() throws Exception {
        // Prepare data
        final ObjectMapper mapper = new ObjectMapper();
        final Supplier supplier = this.supplierRepository.findSupplierByName("Ciela");
        final LocationCreateDTO locationCreateDTO = new LocationCreateDTO("new location", supplier.getId());
        final String requestUrl = "/api/locations";

        // Perform post request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(requestUrl)
                        .content(mapper.writeValueAsString(locationCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Check that the data is saved
        final List<Location> savedLocation = this.locationRepository.findAll(LocationSpecification.hasCriteria(new LocationCriteria("new location")));
        assertNotNull(savedLocation);
        assertEquals(locationCreateDTO.getAddress(), savedLocation.get(0).getAddress());
        assertEquals(locationCreateDTO.getSupplierId(), savedLocation.get(0).getSupplier().getId());
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testEditLocation() throws Exception {
        // Prepare data
        final ObjectMapper mapper = new ObjectMapper();
        final Location location = this.locationRepository.findOne(LocationSpecification.hasCriteria(new LocationCriteria(DEFAULT_ADDRESS))).get();
        final LocationDTO locationDTO = new LocationDTO(location.getId(), "new location");
        final String requestUrl = "/api/locations";

        // Perform patch request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .patch(requestUrl)
                        .content(mapper.writeValueAsString(locationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testEditLocationThrowsOnInvalidLocationId() throws Exception {
        // Prepare data
        final ObjectMapper mapper = new ObjectMapper();
        this.locationRepository.deleteById(MISSING_ID);
        final LocationDTO locationDTO = new LocationDTO(MISSING_ID, "new location");
        final String requestUrl = "/api/locations";

        // Perform patch request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .patch(requestUrl)
                        .content(mapper.writeValueAsString(locationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Location with id " + MISSING_ID + " doesn't exist."));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testDeleteLocation() throws Exception {
        // Prepare data
        final Location location = this.locationRepository.findOne(LocationSpecification.hasCriteria(new LocationCriteria(DEFAULT_ADDRESS))).get();
        final String requestUrl = String.format("/api/locations/%d", location.getId());

        // Perform delete request
        final MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(requestUrl))
                .andExpect(status().isOk())
                .andReturn();

        // Assert that the right data is returned
        assertEquals(result.getResponse().getContentAsString(), location.getId().toString());
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testDeleteLocationThrowsOnInvalidLocationId() throws Exception {
        // Prepare data
        this.locationRepository.deleteById(MISSING_ID);
        final String requestUrl = String.format("/api/locations/%d", MISSING_ID);

        // Perform delete request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(requestUrl))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Location with id " + MISSING_ID + " doesn't exist."));
    }
}
