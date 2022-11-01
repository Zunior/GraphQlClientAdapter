package com.example.GraphQl.adapters;

import com.example.GraphQl.enums.GraphQlOperation;
import com.example.GraphQl.models.Entry;
import com.example.GraphQl.models.EntryFilter;
import com.example.GraphQl.models.LicenseInfo;
import com.example.GraphQl.models.Publication;
import com.example.GraphQl.utils.UtilUtil;
import com.github.k0kubun.builder.query.graphql.GraphQLQueryBuilder;
import org.apache.http.client.CredentialsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

class GraphQlFetchAdapterTest {

    static String serviceUrl;
    static CredentialsProvider provider;

    @BeforeAll
    static void predefinedConfiguration() {
        serviceUrl = "https://api.dev.ginto.guide/graphql";
        provider = UtilUtil.getBasicCredentialsProvider("mario.capitelli@guidle.com", "B2s6deFYpdWA9Pm");
    }

    @Test
    public void testSimpleQuery() throws JSONException, URISyntaxException, IOException {
        String query = getSimpleQuery();
        List<EntryFilter> expectedList = getExpectedListForSimpleQuery();

        GraphQlFetchAdapter adapter = new GraphQlFetchAdapter();
        List<EntryFilter> actualList = adapter.parseGraphQlResponse(EntryFilter.class, serviceUrl, query, GraphQlOperation.QUERY, provider);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    public void testQueryWithParam() throws JSONException, URISyntaxException, IOException {
        Map<String, Object> entryParams = Collections.singletonMap("entryId", "ee659e74-295f-4285-8c3d-d90f232efa44");
        Map<String, Object> externalRatingsParams = Collections.singletonMap("sourceKey", "Z2lkOi8vcmFpbHMtYXBwL1NvdXJjZXM6OlNvdXJjZS96dWVyc3Q");
        Map<String, Object> wheelchairAccessibilityParams = Collections.singletonMap("ratingProfileId", "Z2lkOi8vcmFpbHMtYXBwL1JhdGluZ1Byb2ZpbGVzOjpSYXRpbmdQcm9maWxlLzc4");

        String query = getComplexQuery(entryParams, externalRatingsParams, wheelchairAccessibilityParams);
        List<Entry> expectedList = getExpectedListForComplexQuery();

        GraphQlFetchAdapter adapter = new GraphQlFetchAdapter();
        List<Entry> actualList = adapter.parseGraphQlResponse(Entry.class, serviceUrl, query, GraphQlOperation.QUERY, provider);
        Assertions.assertEquals(expectedList, actualList);

    }

    private List<Entry> getExpectedListForComplexQuery() {
        Entry entry = new Entry();
        entry.setEntryId("ee659e74-295f-4285-8c3d-d90f232efa44");
        entry.setName("St. Gallen Bodensee Tourismus");
        entry.setExternalRatings(Collections.emptyList());
        Publication publication = new Publication();
        entry.setPublication(publication);
        publication.setIconUrl("https://dev.ginto.guide/entries/ee659e74-295f-4285-8c3d-d90f232efa44/sticker.svg");
        publication.setLinkUrl("https://dev.ginto.guide/entries/ee659e74-295f-4285-8c3d-d90f232efa44?lnk_src=business");
        publication.setIconText("OK:GO - Klicken Sie hier für Informationen zur Zugänglichkeit");
        publication.setLinkText("Detaillierte Zugänglichkeitsinfos");
        LicenseInfo licenseInfo = new LicenseInfo();
        entry.setLicenseInfo(licenseInfo);
        licenseInfo.setLicense("Unknown");
        licenseInfo.setOpenData(false);
        licenseInfo.setAttribution("Erfasst durch Pro Infirmis");

        return new ArrayList<Entry>() {{
            add(entry);
        }};
    }

    private static String getSimpleQuery() {
        return GraphQLQueryBuilder.query()
                .object("entryFilters", GraphQLQueryBuilder.object()
                        .field("id")
                        .field("name")
                        .field("qualityLevels")
                        .field("postcodes")
                        .build()
                ).build();
    }

    private static ArrayList<EntryFilter> getExpectedListForSimpleQuery() {
        EntryFilter expectedEntryFilter = new EntryFilter();
        expectedEntryFilter.setId("Z2lkOi8vcmFpbHMtYXBwL0dyb3VwaW5nOjpFbnRyeUdyb3VwL2FiYWRiMzUwLWM1M2QtNGIzZC05NjkxLTY3OWFkMGNmMzgzZQ");
        expectedEntryFilter.setName("Test Guidle");
        expectedEntryFilter.setQualityLevels(new ArrayList<>(Arrays.asList("business", "auditor")));
        expectedEntryFilter.setPostcodes(Collections.emptyList());

        return new ArrayList<EntryFilter>() {{
            add(expectedEntryFilter);
        }};
    }

    private static String getComplexQuery(Map<String, Object> entryParams, Map<String, Object> externalRatingsParams, Map<String, Object> wheelchairAccessibilityParams) {
        return GraphQLQueryBuilder.query()
                .object("entry", entryParams, GraphQLQueryBuilder.object()
                        .field("entryId")
                        .field("name")
                        .object("externalRatings", externalRatingsParams, GraphQLQueryBuilder.object()
                                .field("iconUrl")
                                .field("description")
                                .field("grade")
                                .build()
                        )
                        .object("publication", GraphQLQueryBuilder.object()
                                .field("iconUrl")
                                .field("iconText")
                                .field("linkUrl")
                                .field("linkText")
                                .build())
                        .object("licenseInfo", GraphQLQueryBuilder.object()
                                .field("license")
                                .field("attribution")
                                .field("isOpenData")
                                .build())
                        .object("wheelchairAccessibility: accessibility", wheelchairAccessibilityParams, GraphQLQueryBuilder.object()
                                .field("grade")
                                .build()
                        ).build()
                )
                .build();
    }

}