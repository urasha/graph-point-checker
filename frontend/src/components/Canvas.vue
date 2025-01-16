<template>
  <canvas ref="canvasRef" id="canvas" width="300" height="300" @click="handleClick"></canvas>
</template>

<script setup>
import {ref, watch, onMounted, toRefs} from "vue";

const props = defineProps({
  rValue: {
    type: Number,
    required: true,
  },
});

const {rValue} = toRefs(props);

const canvasRef = ref(null);

const drawGraph = (rValue) => {
  const canvas = canvasRef.value;

  if (!canvas) {
    return;
  }

  const context = canvas.getContext("2d");
  const canvasWidth = canvas.width;
  const canvasHeight = canvas.height;
  const centerX = canvasWidth / 2;
  const centerY = canvasHeight / 2;
  const scale = (rValue / 2.5) * centerX;

  const clearCanvas = () => {
    context.clearRect(0, 0, canvasWidth, canvasHeight);
  };

  const drawAxes = () => {
    context.strokeStyle = "#000";
    context.lineWidth = 1;

    context.beginPath();
    context.moveTo(0, centerY);
    context.lineTo(canvasWidth, centerY);
    context.stroke();

    context.beginPath();
    context.moveTo(centerX, 0);
    context.lineTo(centerX, canvasHeight);
    context.stroke();

    context.font = "bold 9px Arial";
    context.fillStyle = "#000";
    context.textAlign = "center";
    context.textBaseline = "middle";

    context.beginPath();
    context.moveTo(canvasWidth - 10, centerY - 5);
    context.lineTo(canvasWidth, centerY);
    context.lineTo(canvasWidth - 10, centerY + 5);
    context.stroke();
    context.fillText("X", canvasWidth - 15, centerY - 10);

    context.beginPath();
    context.moveTo(centerX - 5, 10);
    context.lineTo(centerX, 0);
    context.lineTo(centerX + 5, 10);
    context.stroke();
    context.fillText("Y", centerX + 15, 10);

    const marks = [
      {x: -scale, y: 0, label: "-R"},
      {x: scale, y: 0, label: "R"},
      {x: -scale / 2, y: 0, label: "-R/2"},
      {x: scale / 2, y: 0, label: "R/2"},
      {x: 0, y: -scale, label: "R"},
      {x: 0, y: scale, label: "-R"},
      {x: 0, y: -scale / 2, label: "R/2"},
      {x: 0, y: scale / 2, label: "-R/2"},
    ];

    for (const mark of marks) {
      if (mark.x) {
        context.beginPath();
        context.moveTo(centerX + mark.x, centerY - 5);
        context.lineTo(centerX + mark.x, centerY + 5);
        context.stroke();
        context.fillText(mark.label, centerX + mark.x, centerY + 15);
      }
      if (mark.y) {
        context.beginPath();
        context.moveTo(centerX - 5, centerY + mark.y);
        context.lineTo(centerX + 5, centerY + mark.y);
        context.stroke();
        context.fillText(mark.label, centerX + 15, centerY + mark.y);
      }
    }
  };

  const drawRectangle = () => {
    context.fillStyle = "rgba(39, 147, 236, 0.8)";
    context.fillRect(centerX, centerY, scale, scale);
  };

  const drawArc = () => {
    context.fillStyle = "rgba(39, 147, 236, 0.8)";
    context.beginPath();
    context.moveTo(centerX, centerY);
    context.arc(centerX, centerY, scale / 2, 1.5 * Math.PI, 2 * Math.PI);
    context.closePath();
    context.fill();
  };

  const drawTriangle = () => {
    context.fillStyle = "rgba(39, 147, 236, 0.8)";
    context.beginPath();
    context.moveTo(centerX, centerY);
    context.lineTo(centerX - scale / 2, centerY);
    context.lineTo(centerX, centerY - scale);
    context.closePath();
    context.fill();
  };

  clearCanvas();
  drawAxes();
  drawRectangle();
  drawArc();
  drawTriangle();
};

const handleClick = (event) => {
  console.log("Canvas clicked", event);
};

onMounted(() => {
  drawGraph(rValue.value);
});

watch(rValue, (newRValue) => {
  drawGraph(newRValue);
});
</script>

<style scoped>
#canvas {
  border: 1px solid #000;
}
</style>
