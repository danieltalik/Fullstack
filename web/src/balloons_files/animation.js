// call Adlib.screenshotterEnd() on the last animation code.
// do not delete initAnimation function since this is the first function that will be called after initialization of defaultValues.
// if ever there is a video for this ad, you can use myVideo as the variable to play the video.
// sample tween codes:
// tween.to("#disclaimerWrapper", {opacity:0.99,duration: 1,ease: "power2.out"},"-=1");
// tween.set("#frame1HeadlineWrapper",{opacity:1})
let tween;
function initAnimation() {
     // place all fluid elements before text resize and css attrib.
     Adlib.textResize(); // This is optional if your build doesn't use text resize you can delete this
     Adlib.templateCSS(defaultValues.cssAttrib); // DO NOT DELETE THIS
	document.querySelector("#mainContent").style.opacity = 1;
     startAnimation();

}
function startAnimation() {
     tween = gsap.timeline();

	if(defaultValues.trigger == "V1"){
		//SET
		tween.set("#frame1HeadlineV1",{opacity:0.99, y:28})
		tween.set("#logoContainerV1",{opacity:0.99, y:-14})
		tween.set("#ctaTextContainerV1",{opacity:0.99, y:-14})
		tween.set("#frame2ImageContainer",{opacity:0.99, x:-728})//show
		tween.set("#gradient",{opacity:0})
		tween.set("#gradient2",{opacity:0})

		//F R A M E 1
		tween.to("#frame1HeadlineV1", {opacity:0.99, y:0, duration:0.3, ease: "sine.out"},"+=0.5");
		tween.to(["#logoContainerV1","#ctaTextContainerV1"], {opacity:0.99, y:0, duration:0.3, ease: "sine.out"},"-=.3");

		//F R A M E 2
		tween.to("#frame1HeadlineContainerV1", {opacity:0.99, x:219,  duration:1, ease: "expo.inOut"},"+=1");
		tween.to("#frame1ContainerV1", {opacity:0.99, x:209,  duration:1, ease: "expo.inOut"},"-=0.8");
		tween.to("#frame2ImageContainer", {opacity:0.99, x:0,  duration:1, ease:"expo.inOut", onComplete:animationEnd},"-=0.8");
	}
	if(defaultValues.trigger == "V2"){
		//SET
		tween.set("#frame1HeadlineV2",{opacity:0.99, y:28})
		tween.set("#logoContainerV2",{opacity:0.99})
		tween.set("#ctaTextContainerV2",{opacity:0.99})
		tween.set("#frame2ImageContainer",{opacity:0.99, x:728})//show
		//tween.set("#gradient",{x:728})
		tween.set("#gradient2",{width:728})

		//F R A M E 1
		// tween.to("#frame1HeadlineV2", {opacity:0.99, y:0, duration:0.3, translateZ:0.01, transform: "50% 50%", ease: "sine.out"},"+=0.5");
		// tween.to(["#logoContainerV2","#ctaTextContainerV2"], {opacity:0.99, y:0, duration:0.3, translateZ:0.01, transform: "50% 50%", ease: "sine.out", onComplete:takeScreenshot},"+=0.5");

		//F R A M E 2
		tween.to("#frame1ContainerV2", {opacity:0.99, x:-214,  duration:1, ease: "expo.inOut"},"+=1");
		tween.to("#frame2ImageContainer", {opacity:0.99, x:0,  duration:1, ease:"expo.inOut"},"-=1");
		//tween.to("#gradient", {duration:1, x:0, ease: "expo.inOut"},"-=1");
		tween.to("#gradient2", {width:0, duration:1, ease: "expo.inOut"},"-=1");
		tween.to("#logoContainerV2", {opacity:0.99, y:-9,  duration:1, ease: "expo.inOut"},"-=0.4");
		tween.to("#ctaTextContainerV2", {opacity:0.99, y:17,  duration:1, ease: "expo.inOut"},"-=1");
		tween.to("#frame1HeadlineV2", {opacity:0.99, y:0, duration:0.3, ease: "sine.out", onComplete:animationEnd},"-=0.5");
		// tween.to("#frame1HeadlineContainerV2", {opacity:0.99, x:0,  duration:1, translateZ:0.01, transform: "50% 50%", ease: "expo.inOut"},"-=0.8");
	}
}
function animationEnd() {
     // call this function on the very end of the last animation.
     takeScreenshot();
     setTimeout(function() {adlibEnd();},1000);
}
