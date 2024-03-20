package com.example.demo.service;

import com.example.demo.domain.Statistic;

import java.util.List;

public class HtmlMaker {


    public String fullHTMLMakerNeedRewrite(List<Statistic> statistic) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        .container {\n" +
                "            display: flex;\n" +
                "            justify-content: space-between;\n" +
                "            width: 100%;\n" +
                "            max-width: 1200px;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "        .team {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .second-team {\n" +
                "            margin-left: auto;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 20px;\n" +
                "        }\n" +
                "       h1 {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        table {\n" +
                "            border-collapse: collapse;\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "        th, td {\n" +
                "            border: 1px solid #ddd;\n" +
                "            padding: 8px;\n" +
                "            text-align: center;\n" +
                "            font-size: 15px;\n" +
                "        }\n" +
                "        th {\n" +
                "            background-color: #f2f2f2;\n" +
                "        }\n" +
                "        img {\n" +
                "            max-width: 100px;\n" +
                "            max-height: 100px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class='container'>\n" +
                "    <div class='team'>\n" +
                "        <p style='font-size: 24px;'>First team bans</p>\n" +
                "        <img src='cid:ban1' width='40px'> <img src='cid:ban2' width='40px'> <img src='cid:ban3' width='40px'>\n" +
                "        <img src='cid:ban4' width='40px'> <img src='cid:ban5' width='40px'>\n" +
                "        <p style='font-size: 24px;'>-----------</p>\n" +
                "    </div>\n" +
                "    <div class='team second-team'>\n" +
                "        <p style='font-size: 24px;'>Second team bans</p>\n" +
                "        <img src='cid:ban6' width='40px'> <img src='cid:ban7' width='40px'> <img src='cid:ban8' width='40px'>\n" +
                "        <img src='cid:ban9' width='40px'> <img src='cid:ban10' width='40px'>\n" +
                "        <p style='font-size: 24px;'>-----------</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "    <h1>Match Summary</h1>\n");

        stringBuilder.append(createTable(statistic));



        return stringBuilder.toString();

    }

    public String createTable(List<Statistic> statistics) {
        StringBuilder table = new StringBuilder();
        table.append("    <table>\n" +
                "        <tr>\n" +
                "        <th>Total Damage Dealt To Champions</th>\n" +
                "        <th>Stat</th>\n" +
                "        <th>Team1</th>\n" +
                "        <th>Team2</th>\n" +
                "        <th>Stat</th>\n" +
                "        <th>Total Damage Dealt To Champions</th>\n" +
                "        </tr>\n");
int statisticsHalfSize = statistics.size() / 2;
        for (int i = 0; i < statisticsHalfSize; i++) {
            table.append("    <tr>\n" +
                    "        <td>" + statistics.get(i).getTotalDamageDealtToChampions() +"</td>\n" +
                    "        <td>" + statistics.get(i).getKills() + "/" + statistics.get(i).getDeaths() + "/" + statistics.get(i).getAssists()  +"</td>\n" +
                    "        <td>\n" +
                    "            <img src='cid:champ" + i +"'>\n" +
                    "            <br>\n" +
                    "            "+ statistics.get(i).getRiotIdGameName() + " #" + statistics.get(i).getRiotIdTagline() + "\n" +
                    "        </td>\n" +
                    "        <td>\n" +
                    "            <img src='cid:champ" + (i+statisticsHalfSize) +"'>\n" +
                    "            <br>\n" +
                    "            "+ statistics.get(i+statisticsHalfSize).getRiotIdGameName() + " #" + statistics.get(i+statisticsHalfSize).getRiotIdTagline() + "\n" +
                    "        </td>\n" +
                    "        <td>" + statistics.get(i+statisticsHalfSize).getKills() + "/" + statistics.get(i+statisticsHalfSize).getDeaths() + "/" + statistics.get(i+statisticsHalfSize).getAssists()  +"</td>\n" +
                    "        <td>" + statistics.get(i+statisticsHalfSize).getTotalDamageDealtToChampions() +"</td>\n" +
                    "    </tr>");
        }
        table.append("</table>");
        return table.toString();
    }
}
