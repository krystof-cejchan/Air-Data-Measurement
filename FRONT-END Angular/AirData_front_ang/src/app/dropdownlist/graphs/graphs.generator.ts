import { ApexAxisChartSeries, YAxisAnnotations } from "ng-apexcharts";
import { ChartOptions } from "./graphs.component";
export function generateChartOptions(
    seriesData: ApexAxisChartSeries = [],
    categoriesDates: string[] = [],
    type: "airQ" | "temp" | "hum"
): ChartOptions {
    const color_opacity = 0.09;
    const title: string =
        type === "airQ"
            ? "Kvalita Vzuchu"
            : type === "temp"
                ? "Teplota"
                : "Vlhkost";
    const freezing = "#036ffc";
    const cold = "#6eb1ff";
    const hot = "#fff52e";
    const burning = "#ffb300";
    const sweltering = "#A8000D";
    const very_low_risk_color = "#00FF00";
    const low_risk_color = "#FFFF2E";
    const medium_risk_color = "#FFBF00";
    const high_risk_color = "#FF5349";
    const very_high_risk_color = "#A8000D";
    var yAxisAnno: YAxisAnnotations[] =
        type === "airQ"
            ? [
                {
                    y: 0,
                    y2: 25,
                    borderColor: "#696969",
                    fillColor: very_low_risk_color,
                    opacity: color_opacity,
                    label: {
                        borderColor: "#fff",
                        style: {
                            fontSize: "10px",
                            color: "#333",
                            background: very_low_risk_color,
                        },
                        text: "Velmi nízká",
                    },
                },
                {
                    y: 25,
                    y2: 50,
                    borderColor: "#696969",
                    fillColor: low_risk_color,
                    opacity: color_opacity,
                    label: {
                        borderColor: "#fff",
                        style: {
                            fontSize: "10px",
                            color: "#333",
                            background: low_risk_color,
                        },
                        text: "Nízká",
                    },
                },
                {
                    y: 50,
                    y2: 75,
                    borderColor: "#696969",
                    fillColor: medium_risk_color,
                    opacity: color_opacity,
                    label: {
                        borderColor: "#fff",
                        style: {
                            fontSize: "10px",
                            color: "#333",
                            background: medium_risk_color,
                        },
                        text: "Střední",
                    },
                },
                {
                    y: 75,
                    y2: 100,
                    borderColor: "#696969",
                    fillColor: high_risk_color,
                    opacity: color_opacity,
                    label: {
                        borderColor: "#fff",
                        style: {
                            fontSize: "10px",
                            color: "#333",
                            background: high_risk_color,
                        },
                        text: "Vysoká",
                    },
                },
                {
                    y: 100,
                    y2: 1000,
                    borderColor: "#696969",
                    fillColor: very_high_risk_color,
                    opacity: color_opacity,
                    label: {
                        borderColor: "#fff",
                        style: {
                            fontSize: "10px",
                            color: "#333",
                            background: very_high_risk_color,
                        },
                        text: "Velmi vysoká",
                    },
                },
            ]
            : type === "temp"
                ? [
                    {
                        y: -20,
                        y2: 0,
                        borderColor: "#696969",
                        fillColor: freezing,
                        opacity: color_opacity,
                        label: {
                            borderColor: "#fff",
                            style: { fontSize: "10px", color: "#333", background: freezing },
                            text: "Zima",
                        },
                    },
                    {
                        y: 0,
                        y2: 10,
                        borderColor: "#696969",
                        fillColor: cold,
                        opacity: color_opacity,
                        label: {
                            borderColor: "#fff",
                            style: { fontSize: "10px", color: "#333", background: cold },
                            text: "Chladno",
                        },
                    },
                    {
                        y: 10,
                        y2: 20,
                        borderColor: "#696969",
                        fillColor: hot,
                        opacity: color_opacity,
                        label: {
                            borderColor: "#fff",
                            style: { fontSize: "10px", color: "#333", background: hot },
                            text: "Tepleji",
                        },
                    },
                    {
                        y: 20,
                        y2: 30,
                        borderColor: "#696969",
                        fillColor: burning,
                        opacity: color_opacity,
                        label: {
                            borderColor: "#fff",
                            style: { fontSize: "10px", color: "#333", background: burning },
                            text: "Teplo",
                        },
                    },
                    {
                        y: 30,
                        y2: 40,
                        borderColor: "#696969",
                        fillColor: sweltering,
                        opacity: color_opacity,
                        label: {
                            borderColor: "#fff",
                            style: {
                                fontSize: "10px",
                                color: "#333",
                                background: sweltering,
                            },
                            text: "Vedro",
                        },
                    },
                ]
                : [
                    {
                        y: 0,
                        y2: 100,
                        borderColor: "#696969",
                        fillColor: "grey",
                        opacity: color_opacity,
                    },
                ];
    return {
        series: seriesData,
        chart_airQ: { height: 350, type: "area" },
        dataLabels: { enabled: !1 },
        stroke: { curve: "smooth" },
        xaxis: { type: "datetime", categories: categoriesDates },
        tooltip: { x: { format: "dd / MM  / yyyy" } },
        title: {
            text: title,
            align: "center",
            margin: 10,
            offsetX: 0,
            offsetY: 0,
            floating: !1,
            style: {
                fontSize: "14px",
                fontWeight: "bold",
                fontFamily: "Monda",
                color: "#263238",
            },
        },
        annotations: { yaxis: yAxisAnno },
    };
}