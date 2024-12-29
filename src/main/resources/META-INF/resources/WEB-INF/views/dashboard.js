document.addEventListener("DOMContentLoaded", async function () {

    var cpuChart = echarts.init(document.getElementById('cpuGauge'));
    var refreshInterval = 3000;
    var autoRefreshEnabled = true;
    var refreshTimer;

    var option = {
      series: [
        {
          type: 'gauge',
          center: ['50%', '60%'],
          startAngle: 200,
          endAngle: -20,
          min: 0,
          max: 100,
          progress: {
            show: true,
            width: 50
          },
          pointer: {
            show: false,
            itemStyle: {
              color: '#61bf61'
            }
          },
          axisLine: {
            lineStyle: {
              width: 30,
              opacity: 0
            }
          },
          axisTick: {
            show: false
          },
          splitLine: {
            show: false
          },
          axisLabel: {
            show: false
          },
          anchor: {
            show: false
          },
          title: {
            show: false
          },
          detail: {
            valueAnimation: true,
            width: '90%',
            lineHeight: 40,
            borderRadius: 8,
            offsetCenter: [0, '15%'],
            fontSize: 22,
            fontWeight: 'bolder',
            formatter: '{value}%',
            color: 'white'
          },
          data: [
            {
              value: 20,
              itemStyle: {
                color: '#61bf61'
              }
            }
          ]
        },
        {
          type: 'gauge',
          center: ['50%', '60%'],
          startAngle: 200,
          endAngle: -20,
          min: 0,
          max: 100,
          itemStyle: {
            color: '#FD7347'
          },
          progress: {
            show: true,
            width: 8,
            itemStyle: {
              color: {
                type: 'linear',
                colorStops: [
                  {
                    offset: 0.6,
                    color: '#61bf61'
                  },
                  {
                    offset: 0.7,
                    color: '#fdbc40'
                  },
                  {
                    offset: 1,
                    color: '#ff0000'
                  }
                ],
                global: false
              }
            }
          },
          pointer: {
            show: false
          },
          axisLine: {
            show: true
          },

          axisTick: {
            show: false
          },
          splitLine: {
            show: false
          },
          axisLabel: {
            show: false
          },
          detail: {
            show: false
          },
          data: [
            {
              value: 100
            }
          ]
        }
      ]
    };
    const apiPath = "/api/v1/dashboard/";

    var url = new URL(document.URL).origin + apiPath;

    async function getData(url) {
        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`Response status: ${response.status}`);
            }

            var json = await response.json();
            return json
        } catch (error) {
            throw new Error(error.message);
        }
    }

    async function updateDashboard() {
        try {
            const data = await getData(url);

            document.getElementById("uptime").textContent = data.uptime;
            document.getElementById("threadCount").textContent = data.threadCount;

            option.series[0].data[0].value = data.cpuUsage;

                var newColor = '';
                if (data.cpuUsage < 30) {
                    newColor = '#61bf61'; // سبز
                } else if (data.cpuUsage < 70) {
                    newColor = '#fdbc40'; // نارنجی
                } else {
                    newColor = '#ff4d4d'; // قرمز
                }

            option.series[0].data[0].itemStyle.color = newColor;


            cpuChart.setOption(option);

            document.getElementById("heapUsed").textContent = data.heapUsedPercentage + '%';
            document.getElementById("nonHeapUsed").textContent = data.nonHeapUsedPercentage + '%';

            document.getElementById("g1Eden").textContent = data.g1EdenSpace + 'MB';
            document.getElementById("g1Old").textContent = data.g1OldGen + 'MB';
            document.getElementById("g1Survivor").textContent = data.g1SurvivorSpace + 'MB';
        } catch (error) {
            console.error("Error updating dashboard:", error.message);
        }
    }

    updateDashboard();

    function startAutoRefresh() {
        refreshTimer = setInterval(updateDashboard, refreshInterval);
    }

    function stopAutoRefresh() {
        clearInterval(refreshTimer);
    }

    document.getElementById('refreshInterval').addEventListener('change', function (e) {
        refreshInterval = parseInt(e.target.value) * 1000; // Convert to milliseconds
        if (autoRefreshEnabled) {
            stopAutoRefresh();
            startAutoRefresh();
        }
    });

    document.getElementById('toggleAutoRefresh').addEventListener('click', function () {
        autoRefreshEnabled = !autoRefreshEnabled;
        if (autoRefreshEnabled) {
            startAutoRefresh();
            this.textContent = 'Disable Auto-Refresh';
        } else {
            stopAutoRefresh();
            this.textContent = 'Enable Auto-Refresh';
        }
    });

    document.getElementById('performGC').addEventListener('click', function () {
        fetch(url + "gc", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            }
        })
    });

    startAutoRefresh();
});


