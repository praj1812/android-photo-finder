package com.example.android.finalproject;

public class VisualClassificationJson {
    public static String getJsonString() {
        return jsonString;
    }

    private static String jsonString = "{\n" +
            "\"images\": [\n" +
            "\t{\n" +
            "\t\"classifiers\": [\n" +
            "\t\t{\n" +
            "\t\t\"classes\": [\n" +
            "\t\t\t{\n" +
            " \t\t\t\"class\": \"curved road\",\n" +
            "\t\t\t\"score\": 0.603\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\"class\": \"path near canal/river\",\n" +
            "\t\t\t\"score\": 0.593\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\"class\": \"bypath\",\n" +
            "\t\t\t\"score\": 0.59,\n" +
            "\t\t\t\"type_hierarchy\": \"/road/bypath\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\"class\": \"road\",\n" +
            "\t\t\t\"score\": 0.628\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            " \t\t\t\"class\": \"laurel oak\",\n" +
            " \t\t\t\"score\": 0.53,\n" +
            " \t\t\t\"type_hierarchy\": \"/plant/tree/laurel oak\"\n" +
            "\t\t\t},\n" +
            " \t\t\t{\n" +
            " \t\t\t\"class\": \"oak\",\n" +
            " \t\t\t\"score\": 0.535\n" +
            " \t\t\t},\n" +
            " \t\t\t{\n" +
            " \t\t\t\"class\": \"tree\",\n" +
            "  \t\t\t\"score\": 0.539\n" +
            "  \t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\"class\": \"plant\",\n" +
            " \t\t\t\"score\": 0.539\n" +
            "  \t\t\t},\n" +
            "\t\t\t{\n" +
            "  \t\t\t\"class\": \"green color\",\n" +
            "\t\t\t\"score\": 0.96\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            " \t\t\t\"class\": \"greenishness color\",\n" +
            " \t\t\t\"score\": 0.806\n" +
            "\t\t\t}\n" +
            " \t\t],\n" +
            "\t\t\"classifier_id\": \"default\",\n" +
            " \t\t\"name\": \"default\"\n" +
            " \t\t}\n" +
            " \t],\n" +
            " \t\"resolved_url\": \"https://firebasestorage.googleapis.com/v0/b/my-awesome-project-e15e0.appspot.com/o/explore_item%2Fimage%3A15337?alt=media&token=a5b8b5dd-c49a-4370-8823-c6dae98ec423\",\n" +
            " \t\"source_url\": \"https://firebasestorage.googleapis.com/v0/b/my-awesome-project-e15e0.appspot.com/o/explore_item%2Fimage%3A15337?alt=media&token=a5b8b5dd-c49a-4370-8823-c6dae98ec423\"\n" +
            " \t}\n" +
            " ],\n" +
            " \"images_processed\": 1\n" +
            "}";
}