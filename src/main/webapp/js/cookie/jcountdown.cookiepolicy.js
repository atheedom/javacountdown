$(document).ready(function () {
  $.cookiesDirective({
    privacyPolicyUri: 'privacy-policy.jsp',
    explicitConsent: false, // false allows implied consent
    position: 'bottom', // top or bottom of viewport
    duration: 10, // display time in seconds
    limit: 0, // limit disclosure appearances, 0 is forever
    message: "The jcountdown.com website uses cookies. By continuing to browse the site, you are agreeing to our use of cookies.", // customise the disclosure message
    fontFamily: 'helvetica', // font style for disclosure panel
    fontColor: '#FFFFFF', // font color for disclosure panel
    fontSize: '12px', // font size for disclosure panel
    backgroundColor: '#004066', // background color of disclosure panel
    backgroundOpacity: '80', // opacity of disclosure panel
    linkColor: '#FFFFFF' // link color in disclosure panel
  });
});
