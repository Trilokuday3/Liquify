import psutil
from plyer import notification
import time

def check_battery():
    while True:
        battery = psutil.sensors_battery()
        plugged = battery.power_plugged
        percent = battery.percent
        if plugged and (percent > 80 or percent < 30):
            show_notification(percent)
        time.sleep(60)  # Check battery every 60 seconds

def show_notification(percent):
    title = "Battery Alert"
    message = f"Your laptop battery is at {percent}% and plugged in!"
    notification.notify(title=title, message=message, timeout=10)

if __name__ == "__main__":
    check_battery()
