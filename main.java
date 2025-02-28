package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class main {

    public static void main(String... args) {
        String filePath = "Data.txt";
        Map<String, List<Map<String, Object>>> data = readJsonData(filePath);

        if (data != null) {
            processData(data);
        } else {
            System.err.println("Error: ไม่พบข้อมูล");
        }
    }

    private static Map<String, List<Map<String, Object>>> readJsonData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(reader, new TypeReference<Map<String, List<Map<String, Object>>>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void processData(Map<String, List<Map<String, Object>>> data) {

        List<Map<String, Object>> nodes = data.get("nodes");
        List<Map<String, Object>> edges = data.get("edges");
        List<String> NodeArray = new ArrayList<>();
        List<String> addressIn = new ArrayList<>();
        List<String> addressOut = new ArrayList<>();

        Map<String, Map<String, Object>> nodeById = new HashMap<>();
        for (Map<String, Object> node : nodes) {
            nodeById.put((String) node.get("id"), node);
        }

        for (Map<String, Object> node : nodes) {
            if ("input".equals(node.get("type"))) {
                NodeArray.add("'input'");
                addressIn.add("''");
            }
        }

        for (Map<String, Object> edge : edges) {
            String target = (String) edge.get("target");
            addressOut.add(String.format("'%s'", target));

            if (nodeById.containsKey(target)) {
                NodeArray.add(String.format("'%s'", nodeById.get(target).get("type")));
                for (Map<String, Object> e : edges) {
                    if (target.equals(e.get("source"))) {
                        addressIn.add(String.format("'%s'", e.get("source")));
                    }
                }
            }
        }
        System.out.println("\nNode = " + NodeArray);
        System.out.println("\naddressIn = " + addressIn);
        System.out.println("\naddressOut = " + addressOut);

    }
}
