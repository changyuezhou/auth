<template>
  <div style="width:100%;height:100%" class="lds-rolling" v-show="$store.state.LOADING.show">
    <div></div>
  </div>
</template>




<style type="text/css">

    @keyframes lds-rolling {
        0% {
            -webkit-transform: translate(-50%, -50%) rotate(0deg);
            transform: translate(-50%, -50%) rotate(0deg);
        }
        100% {
            -webkit-transform: translate(-50%, -50%) rotate(360deg);
            transform: translate(-50%, -50%) rotate(360deg);
        }
    }

    @-webkit-keyframes lds-rolling {
        0% {
            -webkit-transform: translate(-50%, -50%) rotate(0deg);
            transform: translate(-50%, -50%) rotate(0deg);
        }
        100% {
            -webkit-transform: translate(-50%, -50%) rotate(360deg);
            transform: translate(-50%, -50%) rotate(360deg);
        }
    }

    .lds-rolling {
        position: fixed;
        top:70px;
        right:0;
    }

    .lds-rolling div,
    .lds-rolling div:after {
        position: absolute;
        width: 160px;
        height: 160px;
        border: 20px solid #309bff;
        border-top-color: transparent;
        border-radius: 50%;
    }

    .lds-rolling div {
        -webkit-animation: lds-rolling 1s linear infinite;
        animation: lds-rolling 1s linear infinite;
        top: 100px;
        left: 100px;
    }

    .lds-rolling div:after {
        -webkit-transform: rotate(90deg);
        transform: rotate(90deg);
    }

    .lds-rolling {
        width: 40px !important;
        height: 40px !important;
        -webkit-transform: translate(-20px, -20px) scale(0.2) translate(20px, 20px);
        transform: translate(-20px, -20px) scale(0.2) translate(20px, 20px);
    }


</style>