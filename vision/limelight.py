import math

import cv2
import numpy as np

color_range = {
    #blue
    1: [
        # Lower, Upper
        ([100, 65, 65], [130, 255, 255])
    ],
    #red
    0: [
        # For red we need two ranges because the hue value wraps around
        ([176, 127, 65], [180, 255, 255]),
        ([0, 127, 65], [3, 255, 255])
    ]
}

def runPipeline(image, llrobot):

    llpython = [0, 0, 0, 0, 0, 0, 0, 0]

    color_to_detect = llrobot[0]
    print(color_to_detect)
    if color_to_detect in color_range:

        hsv_image = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        color_ranges = color_range[color_to_detect]
        mask = np.zeros((image.shape[0], image.shape[1]))
        for crange in color_ranges:
            mask += cv2.inRange(hsv_image, np.array(crange[0]), np.array(crange[1]))
        contours, hierarchy = cv2.findContours(mask.astype(np.uint8), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

        possible_circles = []

        for c in contours:
            center, radius = cv2.minEnclosingCircle(c)
            circle_area = math.pi * radius ** 2
            if circle_area > 2500:
                cir_density = cv2.contourArea(c) / circle_area
                possible_circles.append((cir_density, circle_area, (int(center[0]), int(center[1])), int(radius)))

        possible_circles.sort(key=lambda x: x[0], reverse=True)

        if len(possible_circles) > 0:
            circle_density, circle_area, center, radius = possible_circles[0]
            x, y = center

            # Plot the points of a circle
            # A dumb way of looping through 0% - 100%
            num_points = 50
            circle_contour = np.zeros((num_points, 1, 2))
            for i in range(0, num_points):
                rads = 2 * math.pi * (1 / num_points) * i
                cx = radius * math.cos(rads) + x
                cy = radius * math.sin(rads) + y
                circle_contour[i, 0, :] = np.asarray([cx, cy])

            llpython[0] = True
            llpython[1] = circle_area
            llpython[2] = x
            llpython[3] = y

            return circle_contour.astype(int), image, llpython

        return [], image, llpython