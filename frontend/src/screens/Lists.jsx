import {
  Container,
  Typography,
  AppBar,
  Toolbar,
  IconButton,
  Avatar,
  Box,
  TextField,
  Button,
  Paper,
  Drawer,
  ListItem,
  ListItemText,
  ListItemButton,
  Modal,
  Popover,
  Alert,
  Rating,
} from "@mui/material";
import InfoIcon from "@mui/icons-material/Info";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useNavigation } from "react-router-dom";
import MenuIcon from "@mui/icons-material/Menu";
import NavigationBar from "../components/NavigationBar";

const Lists = function Lists() {
  const navigate = useNavigate();
  const [createListModalVisible, setCreateListModalVisible] = useState(false); //visibilty of modal for creating new list
  const [listName, setListName] = useState(""); //the name of the list to be added
  const [description, setDescription] = useState(""); //the description of the list
  const [statusModalOpen, setStatusModalOpen] = useState(false); //used to control visibility of feedback after trying to create list
  const [isSuccessful, setIsSuccessful] = useState(false); //create list can be successful or not
  const [categoriesMoviesReviews, setCategoriesMoviesReviews] = useState([]); //dictionary containing the categories, the movies inside each category, and their ratings
  const [listDescriptionEl, setListDescriptionEl] = useState(null); //the element the list description will be attached to
  const [displayedDescription, setDisplayedDescription] = useState(null); //the description of the current popover
  useEffect(() => {
    //find the categories, their movies, and ratings for the users
    axios
      .get("http://localhost:8080/category/getUserCategories", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        console.log(response.data);
        setCategoriesMoviesReviews(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  /**
   * Handles when user tries creating new list
   */
  const createList = () => {
    axios
      .post(
        "http://localhost:8080/category/create",
        {
          title: listName,
          description: description,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      )
      .then((response) => {
        setStatusModalOpen(true);
        setIsSuccessful(true);
        setCreateListModalVisible(false);
      })
      .catch((error) => {
        setStatusModalOpen(true);
        setIsSuccessful(false);
        console.log(error);
      });
  };

  return (
    <Container
      maxWidth={false}
      sx={{
        width: "100vw",
        height: "100vh",
        display: "flex",
        flexDirection: "column",
        justifyContent: "flex-start",
      }}
    >
      <NavigationBar></NavigationBar>
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          marginTop: "100px",
        }}
      >
        <Button
          variant="contained"
          sx={{ backgroundColor: "#db0000" }}
          onClick={() => setCreateListModalVisible(true)}
        >
          Create New List
        </Button>
      </Box>
      <Modal
        open={createListModalVisible}
        onClose={() => setCreateListModalVisible(false)}
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          width: "100vw",
          height: "100vh",
        }}
      >
        <Box
          sx={{
            height: "400px",
            width: "350px",
            backgroundColor: "#FFFFFF",
            borderRadius: 3,
            display: "flex",
            flexDirection: "column",
            justifyContent: "space-evenly",
            alignItems: "center",
          }}
        >
          <TextField
            label="List Title"
            value={listName}
            onChange={(e) => setListName(e.target.value)}
            sx={{ width: "100%" }}
            required
          />
          <TextField
            label="Add list description here"
            multiline
            rows={6}
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            sx={{ width: "100%" }}
            required
          />
          <Button
            variant="contained"
            sx={{ backgroundColor: "#db0000" }}
            onClick={() => createList()}
          >
            Create
          </Button>
        </Box>
      </Modal>
      <Modal
        open={statusModalOpen}
        onClose={() => setStatusModalOpen(false)}
        sx={{
          width: "100vw",
          height: "100vh",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Alert severity={isSuccessful ? "success" : "error"}>
          {isSuccessful
            ? "New list successfully created"
            : "Error creating new list"}
        </Alert>
      </Modal>
      {categoriesMoviesReviews &&
        Array.isArray(categoriesMoviesReviews) &&
        categoriesMoviesReviews.map((item) => (
          <Box
            key={item.id}
            sx={{ heigth: "100%", width: "100vw", marginTop: "20px" }}
          >
            <Box
              sx={{
                display: "flex",
                flexDirection: "row",
                alignItems: "center",
              }}
            >
              <Typography
                sx={{ fontFamily: "sans-serif", fontSize: 24, fontWeight: 600 }}
              >
                {item.title}
              </Typography>
              <IconButton
                onClick={(e) => {
                  setListDescriptionEl(e.currentTarget);
                  setDisplayedDescription(item.description);
                }}
              >
                <InfoIcon color="primary"></InfoIcon>
              </IconButton>
              <Popover
                open={listDescriptionEl ? true : false}
                anchorEl={listDescriptionEl}
                onClose={() => setListDescriptionEl(null)}
                anchorOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
              >
                {displayedDescription}
              </Popover>
            </Box>
            <Box
              sx={{
                width: "100%",
                height: "200px",
                marginTop: "20px",
                display: "flex",
                flexDirection: "row",
              }}
            >
              {item.movies &&
                Array.isArray(item.movies) &&
                item.movies.map((movie) => (
                  <Box
                    key={movie.id}
                    sx={{
                      height: "100%",
                      marginRight: "20px",
                      borderRadius: 3,
                      overflow: "hidden",
                      display:'flex',
                      flexDirection:'column'
                    }}
                  >
                    <img src={movie.img} height="90%"></img>
                    <Rating value={movie.rating.rating} readOnly></Rating>
                  </Box>
                ))}
            </Box>
          </Box>
        ))}
    </Container>
  );
};

export default Lists;
